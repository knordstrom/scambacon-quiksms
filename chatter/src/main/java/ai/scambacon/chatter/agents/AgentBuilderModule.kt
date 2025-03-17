package ai.scambacon.chatter.agents

import ai.scambacon.chatter.data.DecisionManager
import ai.scambacon.chatter.data.PersonaManager
import ai.scambacon.chatter.io.InventiveModelChatFlow
import ai.scambacon.chatter.io.ModelChat
import dagger.Module
import dagger.Provides
import dev.octoshrimpy.quik.migration.ChatterRealmMigration
import dev.octoshrimpy.quik.repository.ConversationRepository
import dev.octoshrimpy.quik.repository.ScheduledMessageRepository
import javax.inject.Inject


@Module
class AgentBuilderModule  @Inject constructor(
    val modelChat: ModelChat,
    val decisionManager: DecisionManager,
    val personaManager: PersonaManager,
    val conversationRepository: ConversationRepository,
    val scheduledMessageRepository: ScheduledMessageRepository
//    ,
//    val realmMigration: ChatterRealmMigration
) {
//    @Provides
//    fun createAgent() : ConversationalAgent = InventiveModelChatFlow(modelChat,
//        decisionManager, personaManager, conversationRepository, scheduledMessageRepository,
//    )//    )realmMigration)
}