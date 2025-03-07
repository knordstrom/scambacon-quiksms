package ai.scambacon.chatter.model

import dev.octoshrimpy.quik.model.Contact
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import java.sql.Timestamp

open class  Decision(
    @PrimaryKey var conversationId: Long = 0L,
    var contact: Persona? = null,
    var status: ApprovalStatus = ApprovalStatus.INITIATING,
    var threadCount: Int = 0,
    var lastUpdated: Timestamp = Timestamp(System.currentTimeMillis())
): RealmModel {

}