package dev.jacobandersen.jorstad.ext

import cloud.commandframework.context.CommandContext
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.data.privileged_users.PrivilegedUser
import dev.jacobandersen.jorstad.data.privileged_users.PrivilegedUserDam
import dev.jacobandersen.jorstad.util.Log
import org.javacord.api.entity.permission.Role
import org.javacord.api.entity.server.Server
import org.javacord.api.entity.user.User

private const val numberHint = "Make sure you're using the numbers and not a ping."
private const val correctHint = "Is it correct?"

fun CommandContext<JavacordCommandSender>.isSenderGuildOwner(ifYes: String?): Boolean {
    val sender = this.sender
    val server = sender.event.server.orElse(null) ?: return false
    val isOwner = sender.author.id == server.ownerId

    if (isOwner) {
        if (ifYes != null) sender.sendErrorMessage(ifYes)
    }

    return isOwner
}
fun CommandContext<JavacordCommandSender>.resolveDiscordRoleFromArgument(guild: Server): Role? {
    val roleId = this.get<String>("role").toLongOrNull()
    if (roleId == null) {
        this.sender.sendErrorMessage("That looks like an invalid role ID. $numberHint")
        return null
    }

    val role = guild.getRoleById(roleId).orElse(null)
    return if (role != null) { role }
    else {
        this.sender.sendErrorMessage("I couldn't find a role with that ID. $correctHint")
        null
    }
}

fun CommandContext<JavacordCommandSender>.resolveDiscordUserFromArgument(guild: Server): User? {
    val userId = this.get<String>("user").toLongOrNull()
    if (userId == null) {
        this.sender.sendErrorMessage("That looks like an invalid user ID. $numberHint")
        return null
    }

    val user = guild.getMemberById(userId).orElse(null)
    return if (user != null) { user }
    else {
        this.sender.sendErrorMessage("I couldn't find a user with that ID. $correctHint")
        null
    }
}

fun <T> CommandContext<JavacordCommandSender>.resolveListFromArgument(arg: String, mapper: (str: String) -> T): List<T> {
    val string = this.get<String>(arg)
    return string.split(",").map(mapper)
}

fun CommandContext<JavacordCommandSender>.resolvePrivilegesFromArgument(): List<PrivilegedUser.Privilege> {
    return resolveListFromArgument("privileges") {
        PrivilegedUser.Privilege.fromString(it)
    }
}