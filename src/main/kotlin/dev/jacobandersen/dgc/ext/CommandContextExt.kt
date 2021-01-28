package dev.jacobandersen.dgc.ext

import cloud.commandframework.context.CommandContext
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.data.privileged_users.PrivilegedUser
import org.javacord.api.entity.permission.Role
import org.javacord.api.entity.server.Server
import org.javacord.api.entity.user.User

private const val correctHint = "Is it correct?"

fun CommandContext<JavacordCommandSender>.isGuildOwner(user: User, ifYes: String?): Boolean {
    val server = sender.event.server.orElse(null) ?: return false
    val isOwner = user.id == server.ownerId

    if (isOwner) {
        if (ifYes != null) sender.sendErrorMessage(ifYes)
    }

    return isOwner
}

fun CommandContext<JavacordCommandSender>.resolveDiscordRoleFromArgument(guild: Server): Role? {
    val rawRole = this.get<String>("role")

    val roleId: Long? = if (rawRole.matchesRoleRegex()) {
        rawRole.getIdFromRoleMention()
    } else {
        rawRole.toLongOrNull()
    }

    if (roleId == null) {
        this.sender.sendErrorMessage("That looks like an invalid role ID.")
        return null
    }

    val role = guild.getRoleById(roleId).orElse(null)
    return if (role != null) {
        role
    } else {
        this.sender.sendErrorMessage("I couldn't find a role with that ID. $correctHint")
        null
    }
}

fun CommandContext<JavacordCommandSender>.resolveDiscordUserFromArgument(guild: Server): User? {
    val rawUser = this.get<String>("user")

    val userId: Long? = if (rawUser.matchesUserRegex()) {
        rawUser.getIdFromUserMention()
    } else {
        rawUser.toLongOrNull()
    }

    if (userId == null) {
        this.sender.sendErrorMessage("That looks like an invalid user.")
        return null
    }

    val user = guild.getMemberById(userId).orElse(null)
    return if (user != null) {
        user
    } else {
        this.sender.sendErrorMessage("I couldn't find a user with that ID. $correctHint")
        null
    }
}

fun <T> CommandContext<JavacordCommandSender>.resolveListFromArgument(
    arg: String,
    mapper: (str: String) -> T
): List<T> {
    val string = this.get<String>(arg)
    return string.split(",").map(mapper)
}

fun CommandContext<JavacordCommandSender>.resolvePrivilegesFromArgument(): List<PrivilegedUser.Privilege> {
    return resolveListFromArgument("privileges") {
        PrivilegedUser.Privilege.fromString(it)
    }
}