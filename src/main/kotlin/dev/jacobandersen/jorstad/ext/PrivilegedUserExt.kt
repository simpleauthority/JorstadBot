package dev.jacobandersen.jorstad.ext

import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.data.privileged_users.PrivilegedUserDam

fun PrivilegedUserDam.alreadyExists(sender: JavacordCommandSender, guildId: Long, userId: Long): Boolean {
    if (this.privilegedUserExists(guildId, userId)) {
        sender.sendErrorMessage("That user is already privileged. Did you mean to add privileges?")
        return true
    }

    return false
}

fun PrivilegedUserDam.doesNotExist(sender: JavacordCommandSender, guildId: Long, userId: Long): Boolean {
    if (!this.privilegedUserExists(guildId, userId)) {
        sender.sendErrorMessage("That user is not privileged! Did you specify the right user?")
        return true
    }

    return false
}