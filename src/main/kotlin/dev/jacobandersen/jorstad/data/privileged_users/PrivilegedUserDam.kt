package dev.jacobandersen.jorstad.data.privileged_users

import dev.jacobandersen.jorstad.manager.DataManager

class PrivilegedUserDam(manager: DataManager) {
    private val dao = manager.jdbi.onDemand(PrivilegedUserDao::class.java)

    fun createContainer() {
        dao.createContainer()
    }

    fun privilegedUserExists(guildId: Long, userId: Long): Boolean {
        return dao.privilegedUserExists(guildId, userId)
    }

    fun addPrivilegedUser(guildId: Long, userId: Long, privileges: List<PrivilegedUser.Privilege>) {
        dao.addPrivilegedUser(guildId, userId, privileges.joinToString())
    }

    fun findPrivilegedUser(guildId: Long, userId: Long): PrivilegedUser? {
        return dao.findPrivilegedUser(guildId, userId)
    }

    fun updatePrivilegedUser(guildId: Long, userId: Long, mutator: (privileges: MutableList<PrivilegedUser.Privilege>) -> List<PrivilegedUser.Privilege>) {
        val user = dao.findPrivilegedUser(guildId, userId) ?: return
        dao.updatePrivilegedUser(guildId, userId, mutator.invoke(user.privileges.toMutableList()).joinToString())
    }

    fun deletePrivilegedUser(guildId: Long, userId: Long) {
        dao.deletePrivilegedUser(guildId, userId)
    }

    fun deletePrivilegedUsers(guildId: Long) {
        dao.deletePrivilegedUsers(guildId)
    }
}