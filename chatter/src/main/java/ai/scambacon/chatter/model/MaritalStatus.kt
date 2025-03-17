package ai.scambacon.chatter.model

import io.ktor.util.toUpperCasePreservingASCIIRules


enum class MaritalStatus {
    SINGLE, MARRIED, SEPARATED, DIVORCED, WIDOWED, POLYAMOROUS;

    companion object {
        fun fromString(value: String?): MaritalStatus {
            return value?.let { MaritalStatus.valueOf(it.toUpperCasePreservingASCIIRules()) } ?: SINGLE
        }
    }
}