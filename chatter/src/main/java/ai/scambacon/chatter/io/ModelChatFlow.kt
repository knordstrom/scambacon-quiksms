package ai.scambacon.chatter.io

import dagger.Module
import dagger.Provides
import ai.scambacon.chatter.model.Decision
import dev.octoshrimpy.quik.model.Conversation
import dev.octoshrimpy.quik.model.Message

@Module
interface ModelChatFlow {

    /**
     * Assumes a new message has updated the conversation. Makes an evaluation, potentially performs
     * a texting action, and returns a decision. If the status is
     *      INITIATING:
     *          evaluation if the person is known -> KNOWN
     *          evaluation if the person is unknown -> UNKNOWN (and initiate silly conversation)
     *      UNKNOWN:
     *          evaluation if the person is known -> KNOWN (apologize for the confusion)
     *      KNOWN:
     *          passes through, no action
     *
     */
    @Provides
    fun converse(conversation: Conversation): Decision
}