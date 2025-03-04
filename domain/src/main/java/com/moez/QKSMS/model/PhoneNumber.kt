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
package dev.octoshrimpy.quik.model

import android.telephony.PhoneNumberUtils
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PhoneNumber(
    @PrimaryKey var id: Long = 0,
    var accountType: String? = "",
    var address: String = "",
    var type: String = "",
    var isDefault: Boolean = false
) : RealmObject() {

    fun hasSameAddress(other: PhoneNumber?): Boolean {
        return other?.let { o -> PhoneNumberUtils.compare(address, o.address)} == true

//        other?.let { n ->
//            val a = n.address.replace("-","")
//            val baseAddress = address.replace("-","")
//            baseAddress.endsWith(a)
//        } == true
    }
}


