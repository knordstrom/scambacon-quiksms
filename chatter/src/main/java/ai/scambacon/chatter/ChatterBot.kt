package ai.scambacon.chatter

import ai.scambacon.chatter.io.ModelChat
import ai.scambacon.chatter.model.Decision
import ai.scambacon.chatter.model.Persona
import dev.octoshrimpy.quik.model.Conversation
import dev.octoshrimpy.quik.model.Message
import javax.inject.Inject

class ChatterBot @Inject constructor(private val chat: ModelChat) {

    fun createPersona(message: Message): Persona {
        TODO("Not yet implemented")
    }

    fun continueConversation(conversation: Conversation): Conversation {
        TODO("Not yet implemented")
    }

    fun validateConversation(conversation: Conversation): Decision {
        TODO("Not yet implemented")
    }

}