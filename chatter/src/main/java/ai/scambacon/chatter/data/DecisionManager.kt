package ai.scambacon.chatter.data

import ai.scambacon.chatter.model.Decision
import dev.octoshrimpy.quik.model.Conversation

interface DecisionManager {

    fun evaluateConversation(conversation: Conversation, lastDecision: Decision): Decision

    fun getOrCreate(conversation: Conversation): Decision

    fun updateDecision(decision: Decision): Unit

    fun incrementDecisionMessagedCount(decision: Decision, lastMessage: String): Decision

    fun createDecision(decision: Decision): Unit
}