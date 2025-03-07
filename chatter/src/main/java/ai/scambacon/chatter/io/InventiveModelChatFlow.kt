package ai.scambacon.chatter.io

import ai.scambacon.chatter.data.DecisionManager
import ai.scambacon.chatter.data.PersonaManager
import ai.scambacon.chatter.model.Decision
import ai.scambacon.chatter.model.ApprovalStatus
import dev.octoshrimpy.quik.model.Conversation
import dev.octoshrimpy.quik.model.Message
import java.sql.Timestamp
import javax.inject.Inject

class InventiveModelChatFlow @Inject constructor(
    val modelChat: ModelChat,
    val decisionManager: DecisionManager,
    val personaManager: PersonaManager
): ModelChatFlow {

    val INITATE_ATTEMPTS = 3

    private fun incrementDecision(decision: Decision): Decision {
        decision.threadCount++
        decisionManager.updateDecision(decision)
        return decision
    }

    fun initialSteps(lastDecision: Decision, conversation: Conversation): Decision {
        val evaluationDecision = modelChat.evaluate(conversation, lastDecision)
        if (evaluationDecision.status == ApprovalStatus.KNOWN) {
            decisionManager.updateDecision(evaluationDecision)
            return evaluationDecision
        }

        if (evaluationDecision.threadCount < INITATE_ATTEMPTS) {
            modelChat.initiateConversation(conversation)
            return incrementDecision(lastDecision)
        }
        else {
            //time to move on to the fun phase
            return funPhase(lastDecision, conversation)
        }
    }

    private fun funPhase(lastDecision: Decision, conversation: Conversation): Decision {
        val evaluationDecision = modelChat.evaluate(conversation, lastDecision)
        if (evaluationDecision.status == ApprovalStatus.KNOWN) {
            decisionManager.updateDecision(evaluationDecision)
            return evaluationDecision
        }

        val response = modelChat.continueBullshitConversation(conversation)
//

        //TODO analyze last message in conversation to add to decision persona

        return incrementDecision(lastDecision)
    }



    override fun converse(conversation: Conversation): Decision {
        val lastDecision = decisionManager.getOrCreate(conversation)

        when (lastDecision.status) {
            ApprovalStatus.INITIATING -> return initialSteps(lastDecision, conversation)
            ApprovalStatus.KNOWN -> return lastDecision
            ApprovalStatus.UNKNOWN -> return funPhase(lastDecision, conversation)
        }

    }
}