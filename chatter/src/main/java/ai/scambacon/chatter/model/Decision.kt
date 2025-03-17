package ai.scambacon.chatter.model

import dev.octoshrimpy.quik.model.Contact
import dev.octoshrimpy.quik.model.Message
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.sql.Timestamp

@RealmClass
open class Decision(
    @PrimaryKey var conversationId: Long = 0L,
    var contactPersonaId: Long? = null,
    var status: String = ApprovalStatus.INITIATING.name,
    var threadCount: Int = 0,
    var lastUpdated: Long = System.currentTimeMillis(),
    var lastMessage: String? = null
): RealmObject() {

    fun approvalStatus() = ApprovalStatus.valueOf(status)

}