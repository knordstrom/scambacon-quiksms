package ai.scambacon.chatter.model

import dev.octoshrimpy.quik.model.Conversation
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import java.util.Date

open class Persona(
    @PrimaryKey var id: Long = 0,
    @Index var conversationId: Long,
    var name: String?,
    var occupation: String?,
    var married: MaritalStatus?,
    var backstory: String?,
    var timestamp: Date,
    var updates: List<String> = emptyList() ) : RealmObject () {

        fun forProfile(): String {
            return "{ \"conversationId\": $conversationId, \"name\": \"$name\", \"occupation\": \"$occupation\", \"married\": \"$married\", \"backstory\": \"$backstory\", \"timestamp\": \"$timestamp\", \"updates\": [${updates.joinToString(",")}] }"
        }
}