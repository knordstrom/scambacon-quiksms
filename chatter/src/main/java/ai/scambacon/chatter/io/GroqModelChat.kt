package ai.scambacon.chatter.io

import ai.scambacon.chatter.data.PersonaManager
import ai.scambacon.chatter.model.Decision
import ai.scambacon.chatter.model.ApprovalStatus
import ai.scambacon.chatter.model.Persona
import dev.octoshrimpy.quik.model.Conversation
import dev.octoshrimpy.quik.model.Message
import dev.octoshrimpy.quik.repository.MessageRepository
import ai.scambacon.chatter.groq.GroqSDK
import ai.scambacon.chatter.model.MaritalStatus
import io.realm.Realm
import org.json.JSONObject
import java.sql.Timestamp


import javax.inject.Inject


class GroqModelChat @Inject constructor(
    private val promptManager: PromptManager,
    private val messageRepository: MessageRepository,
    private val personaManager: PersonaManager,
    private val groqSDK: GroqSDK
): ModelChat {

    final val INITIATE_PROMPT = "initiate_conversation"
    final val COMPLETE_PROMPT = "continue_conversation"
    final val EVALUATE_PROMPT = "validate_contact"
    final val PERSONA_PROMPT = "create_persona"

    private fun parseResponse(response: String?): String? {
        if (response == null) {
            return null
        }
        val regex = Regex("<RESPONSE>(.*?)</RESPONSE>", RegexOption.DOT_MATCHES_ALL)
        val matchResult = regex.find(response)
        val extractedText = matchResult?.groups?.get(1)?.value?.trim()

        return extractedText
    }
    private fun getScript(conversation: Conversation): String {
        val messages = messageRepository.getMessages(
            conversation.id
        ).map { message: Message? ->
            if (message != null) "${message.address}: ${message.body}" else ""
        }
        val result = messages.joinToString("\n")
        return result
//        return messages.joinToString { "\n" }
    }

    override fun initiateConversation(conversation: Conversation): String? {
        val message = this.getScript(conversation)
        val context = mapOf("MESSAGE_TEXT" to message)
        val prompt = promptManager.getPrompt(INITIATE_PROMPT, context)
        val response = groqSDK.getResponse(prompt)
        return parseResponse(response)
    }

    fun getOrCreatePersona(conversation: Conversation): Persona {
        var persona = personaManager.getPersonaForConversation(conversation)
        if (persona == null) {
            persona = generatePersona(conversation)
            personaManager.writePersona(persona)
        }
        return persona
    }

    override fun continueBullshitConversation(conversation: Conversation): String? {
        val conversationText = this.getScript(conversation)
        val persona = getOrCreatePersona(conversation)

        val context = mapOf(
            "PERSONA" to persona.forProfile(),
            "NAME" to conversation.name,
            "MESSAGE_TEXT" to conversationText
        )
        val prompt = promptManager.getPrompt(COMPLETE_PROMPT, context)
        val response = groqSDK.getResponse(prompt)
        return parseResponse(response)
    }

    override fun evaluate(conversation: Conversation, lastDecision: Decision): Decision {
        val conversationText = this.getScript(conversation)
        val context = mapOf(
            "CONVERSATION" to conversationText
        )
        val prompt = promptManager.getPrompt(EVALUATE_PROMPT, context)
        val response = groqSDK.getResponse(prompt)
        val decisionValue = parseResponse(response)

        val newDecision = Decision(
            conversationId = conversation.id,
            contactPersonaId = lastDecision.contactPersonaId,
            status = (decisionValue?.let { ApprovalStatus.fromString(it) } ?: ApprovalStatus.UNKNOWN).name,
            threadCount = lastDecision.threadCount,
            lastUpdated = System.currentTimeMillis()
        )
        return newDecision
    }

    override fun generatePersona(conversation: Conversation): Persona {
        val conversationText = this.getScript(conversation)
        val context = mapOf(
            "PERSONA_FIELDS" to "name, occupation, married, backstory",
        )
        val prompt = promptManager.getPrompt(PERSONA_PROMPT, context)
        val response = groqSDK.getResponse(prompt)
        val personaJson = parseResponse(response)
        val obj =  if (personaJson != null) JSONObject(personaJson) else null

        return Persona(
            conversationId = conversation.id,
            name = obj?.getString("name"),
            occupation = obj?.getString("occupation"),
            married = MaritalStatus.fromString(obj?.getString("married") ?: "").name,
            backstory = obj?.getString("backstory"),
            timestamp = System.currentTimeMillis()
        )
    }
}