package ai.scambacon.chatter.data

import dagger.Module
import ai.scambacon.chatter.model.Persona
import dagger.Provides
import dev.octoshrimpy.quik.model.Conversation

@Module
interface PersonaManager {

    @Provides
    fun getPersonaForConversation(conversation: Conversation): Persona

    @Provides
    fun writePersona(persona: Persona): Boolean

    @Provides
    fun updatePersona(persona: Persona): Boolean

}