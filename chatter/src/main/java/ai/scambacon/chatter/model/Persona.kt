package ai.scambacon.chatter.model

import dev.octoshrimpy.quik.model.Contact
import dev.octoshrimpy.quik.model.Conversation
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.Date

@RealmClass
open class Persona(
    @PrimaryKey var id: Long = 0,
    @Index var conversationId: Long? = null,
    var name: String? = null,
    var occupation: String? = null,
    var married: String? = null,
    var backstory: String? = null,
    var timestamp: Long = 0,
    var updates: RealmList<String> = RealmList() ) : RealmObject () {

        fun forProfile(): String {
            return "{ \"conversationId\": $conversationId, \"name\": \"$name\", \"occupation\": \"$occupation\", \"married\": \"$married\", \"backstory\": \"$backstory\", \"timestamp\": \"$timestamp\", \"updates\": [${updates.joinToString(",")}] }"
        }
}