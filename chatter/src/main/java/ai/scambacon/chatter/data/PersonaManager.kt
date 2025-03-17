package ai.scambacon.chatter.data

import ai.scambacon.chatter.model.Persona
import dev.octoshrimpy.quik.model.Conversation

interface PersonaManager {

    fun getPersonaForConversation(conversation: Conversation): Persona?

    fun writePersona(persona: Persona): Unit

    fun updatePersona(persona: Persona): Unit

}