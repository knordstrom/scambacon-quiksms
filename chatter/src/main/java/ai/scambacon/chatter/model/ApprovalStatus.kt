package ai.scambacon.chatter.model

enum class ApprovalStatus {
    INITIATING, UNKNOWN, KNOWN;

    companion object {
        fun fromString(value: String?): ApprovalStatus {
            return value?.let { ApprovalStatus.valueOf(it) } ?: UNKNOWN
        }
    }
}