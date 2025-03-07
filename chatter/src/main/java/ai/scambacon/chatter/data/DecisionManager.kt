package ai.scambacon.chatter.data

import ai.scambacon.chatter.model.Decision
import dagger.Module
import dagger.Provides
import dev.octoshrimpy.quik.model.Conversation

@Module
interface DecisionManager {

    @Provides
    fun evaluateConversation(conversation: Conversation, lastDecision: Decision): Decision

    @Provides
    fun getOrCreate(conversation: Conversation): Decision

    @Provides
    fun updateDecision(decision: Decision): Unit

    @Provides
    fun createDecision(decision: Decision): Unit
}