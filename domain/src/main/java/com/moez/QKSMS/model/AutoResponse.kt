package com.moez.QKSMS.model

open class AutoResponse(
    var contactName: String? = null,
    var message: String? = null,
    var threadId: Long = 0,
    var markKnown: Boolean = false,
    var delay: Int = 0
) {
}