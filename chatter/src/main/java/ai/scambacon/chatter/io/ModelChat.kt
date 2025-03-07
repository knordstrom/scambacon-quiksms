package ai.scambacon.chatter.io

import ai.scambacon.chatter.model.Decision
import ai.scambacon.chatter.model.Persona
import dagger.Module
import dagger.Provides
import dev.octoshrimpy.quik.model.Conversation
import dev.octoshrimpy.quik.model.Message

@Module
interface ModelChat {

    /**
     * Uses an initial contact message and a prompt to generate the start of a conversation
     */
    @Provides
    fun initiateConversation(conversation: Conversation): String?

    /**
     * Uses an existing conversation to generate a completion message
     */
    @Provides
    fun continueBullshitConversation(conversation: Conversation): String?

    /**
     * Uses an existing conversation to evaluate the contact
     */
    @Provides
    fun evaluate(conversation: Conversation, lastDecision: Decision): Decision

    /**
     * Uses an existing conversation to generate an interesting but invented persona
     */
    @Provides
    fun generatePersona(conversation: Conversation): Persona

}