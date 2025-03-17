package ai.scambacon.chatter.data

import ai.scambacon.chatter.model.Decision
import ai.scambacon.chatter.model.Persona
import dev.octoshrimpy.quik.model.Conversation
import io.realm.Realm
import java.sql.Timestamp

class RealmPersonaManager(): PersonaManager {
    override fun getPersonaForConversation(conversation: Conversation): Persona? {
        return Realm.getDefaultInstance()
            .also { realm -> realm.refresh() }
            .where(Persona::class.java)
            .equalTo("conversationId", conversation.id)
            .findFirst()
    }

    override fun writePersona(persona: Persona): Unit {
        persona.timestamp = System.currentTimeMillis()
        Realm.getDefaultInstance().use { realm ->
            realm.refresh()
            realm.executeTransaction {
                realm.insertOrUpdate(persona)
            }
        }
    }

    override fun updatePersona(persona: Persona): Unit {
        return writePersona(persona)
    }
}