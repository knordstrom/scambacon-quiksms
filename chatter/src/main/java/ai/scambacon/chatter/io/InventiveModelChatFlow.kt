package ai.scambacon.chatter.io

import ai.scambacon.chatter.data.DecisionManager
import ai.scambacon.chatter.data.PersonaManager
import ai.scambacon.chatter.model.Decision
import ai.scambacon.chatter.model.ApprovalStatus
import ai.scambacon.chatter.agents.ConversationalAgent
import android.app.Application
import com.moez.QKSMS.model.AutoResponse
import dev.octoshrimpy.quik.model.Conversation
import dev.octoshrimpy.quik.repository.ConversationRepository
import dev.octoshrimpy.quik.repository.ScheduledMessageRepository
import javax.inject.Inject

class InventiveModelChatFlow @Inject constructor(
    val modelChat: ModelChat,
    val decisionManager: DecisionManager,
    val personaManager: PersonaManager,
    val conversationRepository: ConversationRepository
): Application(), ModelChatFlow, ConversationalAgent {

    val INITATE_ATTEMPTS = 10
    val MESSAGE_DELAY = 10000 //TODO: make this configurable

    fun initialSteps(lastDecision: Decision, conversation: Conversation): Decision {
        val evaluationDecision = modelChat.evaluate(conversation, lastDecision)
        if (evaluationDecision.status == ApprovalStatus.KNOWN.name) {
            decisionManager.updateDecision(evaluationDecision)
            return evaluationDecision
        }

        if (evaluationDecision.threadCount < INITATE_ATTEMPTS) {
            val response = modelChat.initiateConversation(conversation)
            val newDecision = decisionManager.incrementDecisionMessagedCount(evaluationDecision, response!!)
            return newDecision
        }
        else {
            //time to move on to the fun phase
            return funPhase(lastDecision, conversation)
        }
    }

    private fun funPhase(lastDecision: Decision, conversation: Conversation): Decision {
        val evaluationDecision = modelChat.evaluate(conversation, lastDecision)
        if (evaluationDecision.status == ApprovalStatus.KNOWN.name) {
            decisionManager.updateDecision(evaluationDecision)
            return evaluationDecision
        }

        val response = modelChat.continueBullshitConversation(conversation)

        //TODO analyze last message in conversation to add to decision persona
        return decisionManager.incrementDecisionMessagedCount(lastDecision, response!!)
    }

    override fun converse(conversation: Conversation): Decision {
        val lastDecision = decisionManager.getOrCreate(conversation)

        when (lastDecision.approvalStatus()) {
            ApprovalStatus.INITIATING -> return initialSteps(lastDecision, conversation)
            ApprovalStatus.KNOWN -> return lastDecision
            ApprovalStatus.UNKNOWN -> return funPhase(lastDecision, conversation)
        }

    }

    override fun converse(threadId: Long): AutoResponse {
        val conversation = conversationRepository.getConversation(threadId)

        if (conversation == null) {
            return AutoResponse(
                contactName = null,
                message = null,
                threadId = threadId,
                markKnown = false
            )
        }
        val decision = converse(conversation)
        val otherPerson = personaManager.getPersonaForConversation(conversation)

        return AutoResponse(
            contactName = otherPerson?.name,
            message = decision.lastMessage,
            threadId = threadId,
            markKnown = decision.status == ApprovalStatus.KNOWN.name,
            delay = MESSAGE_DELAY
        )
    }
}