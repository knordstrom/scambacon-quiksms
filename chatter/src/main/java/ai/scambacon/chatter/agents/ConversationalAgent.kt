package ai.scambacon.chatter.agents

import com.moez.QKSMS.model.AutoResponse
import dagger.Component
import dagger.Module
import dagger.Provides

interface ConversationalAgent {

    fun converse(threadId: Long): AutoResponse
}