package ai.scambacon.chatter.model


enum class MaritalStatus {
    SINGLE, MARRIED, SEPARATED, DIVORCED, POLYAMOROUS;

    companion object {
        fun fromString(value: String?): MaritalStatus {
            return value?.let { MaritalStatus.valueOf(it) } ?: SINGLE
        }
    }
}