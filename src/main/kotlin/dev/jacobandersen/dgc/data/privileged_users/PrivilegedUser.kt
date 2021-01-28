package dev.jacobandersen.dgc.data.privileged_users

import cloud.commandframework.permission.CommandPermission
import cloud.commandframework.permission.Permission
import java.lang.IllegalArgumentException

data class PrivilegedUser(
    val id: Int,
    val guildId: Long,
    val userId: Long,
    val privileges: List<Privilege>
) {
    enum class Privilege {
        ALL,
        FACTOID_COMMAND,
        ROLE_COMMAND,
        PRIVILEGED_USER_COMMAND,
        CONFIG_COMMAND;

        fun permission(): CommandPermission {
            return Permission.of(name)
        }

        fun hasPermission(user: PrivilegedUser): Boolean {
            if (user.privileges.contains(ALL)) return true
            return user.privileges.contains(this)
        }

        companion object {
            fun fromString(str: String): Privilege? {
                return try {
                    valueOf(str.trim().toUpperCase())
                } catch (ignored: IllegalArgumentException) {
                    null
                }
            }
        }
    }
}