package com.moez.QKSMS.model

import android.telephony.SmsMessage

class SmsMessageWrapper(
    val message: SmsMessage,
    val isNewContact: Boolean) {
}