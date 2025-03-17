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
package dev.octoshrimpy.quik.migration

import android.annotation.SuppressLint
import dev.octoshrimpy.quik.util.Preferences
import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration
import javax.inject.Inject

class ChatterRealmMigration @Inject constructor(
) : RealmMigration {

    companion object {
        const val SchemaVersion: Long = 1L
    }

    private val migrate0 = { realm: DynamicRealm ->
        if (!realm.schema.contains("Persona")) {
            realm.schema.create("Persona")
                .addField(
                    "id",
                    Long::class.java,
                    FieldAttribute.PRIMARY_KEY,
                    FieldAttribute.REQUIRED
                )
                .addField("conversationId", Long::class.java, FieldAttribute.REQUIRED)
                .addField("name", String::class.java)
                .addField("occupation", String::class.java)
                .addField("married", String::class.java)
                .addField("backstory", String::class.java)
                .addField("timestamp", Long::class.java, FieldAttribute.REQUIRED)
                .addRealmListField("updates", String::class.java)
        }

        if (!realm.schema.contains("Decision")) {
            realm.schema.create("Decision")
                .addField(
                    "conversationId",
                    Long::class.java,
                    FieldAttribute.PRIMARY_KEY,
                    FieldAttribute.REQUIRED
                )
                .addField("contactPersonaId", Long::class.java)
                .addField("status", String::class.java)
                .addField("threadCount", Int::class.java, FieldAttribute.REQUIRED)
                .addField("lastMessage", String::class.java)
                .addField("lastUpdated", Long::class.java)

        }
        Unit
    }

    var migrations = listOf(migrate0)

    private fun doMigration(version: Int, realm: DynamicRealm): Int {
        if (version > 0) {
            val exec: (DynamicRealm) -> Unit = migrations.get(version - 1)
            exec(realm)
            return version + 1
        }
        return version
    }

    @SuppressLint("ApplySharedPref")
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var version = oldVersion.toInt()

        for (targetVersion in oldVersion .. newVersion) {
            version = doMigration(version, realm)
        }

        check(version >= newVersion) { "Migration missing from v$oldVersion to v$newVersion" }
    }

}
