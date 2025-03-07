package ai.scambacon.chatter.data

import ai.scambacon.chatter.io.ModelChat
import ai.scambacon.chatter.model.Decision
import dev.octoshrimpy.quik.model.Conversation
import dev.octoshrimpy.quik.model.Message
import io.realm.Realm
import java.sql.Timestamp
import javax.inject.Inject

class RealmDecisionManager @Inject constructor(
    val modelChat: ModelChat
): DecisionManager {

    override fun evaluateConversation(conversation: Conversation, lastDecision: Decision): Decision {
        val decision = modelChat.evaluate(conversation, lastDecision)
        return Decision(lastDecision.conversationId, lastDecision.contact,
            decision.status, lastDecision.threadCount, lastDecision.lastUpdated)
    }


    override fun getOrCreate(conversation: Conversation): Decision {
        var decision = getById(conversation.id)
        if (decision == null) {
            decision = Decision(conversation.id)
            createDecision(decision)
        }
        return decision
    }

    fun getById(conversationId: Long): Decision? {
        return Realm.getDefaultInstance()
            .also { realm -> realm.refresh() }
            .where(Decision::class.java)
            .equalTo("conversationId", conversationId)
            .findFirst()
    }

    override fun updateDecision(decision: Decision): Unit {
        decision.lastUpdated = Timestamp(System.currentTimeMillis())
        Realm.getDefaultInstance().insertOrUpdate(decision)
    }

    override fun createDecision(decision: Decision): Unit {
        Realm.getDefaultInstance().insert(decision)
    }
}