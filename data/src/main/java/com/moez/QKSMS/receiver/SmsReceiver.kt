/*
 * Copyright (C) 2017 Moez Bhatti <moez.bhatti@gmail.com>
 *
 * This file is part of QKSMS.
 *
 * QKSMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QKSMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QKSMS.  If not, see <http://www.gnu.org/licenses/>.
 */
package dev.octoshrimpy.quik.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony.Sms
import android.telephony.SmsMessage
import com.moez.QKSMS.model.SmsMessageWrapper
import dev.octoshrimpy.quik.interactor.ReceiveSms
import dagger.android.AndroidInjection
import dev.octoshrimpy.quik.repository.ContactRepository
import timber.log.Timber
import javax.inject.Inject

class SmsReceiver : BroadcastReceiver() {

    @Inject lateinit var receiveMessage: ReceiveSms
    @Inject lateinit var contacts: ContactRepository

    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context)
        Timber.v("onReceive")

        Sms.Intents.getMessagesFromIntent(intent)?.let { messages ->
            val subId = intent.extras?.getInt("subscription", -1) ?: -1
            val messagesWithContacts = messages.map { message: SmsMessage ->
                val address = message.originatingAddress
                val isContact = address?.let{ a -> contacts.isNewContact(a)} == true
                SmsMessageWrapper(message, isContact)
            }

            val pendingResult = goAsync()
            val params = ReceiveSms.Params(subId, messagesWithContacts)
            receiveMessage.execute(params) { pendingResult.finish() }
        }
    }

}