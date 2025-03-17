package ai.scambacon.chatter.agents

import com.moez.QKSMS.model.AutoResponse

interface ConversationalAgent {

    fun converse(threadId: Long): AutoResponse
}