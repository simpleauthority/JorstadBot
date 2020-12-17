package dev.jacobandersen.jorstad.listeners

import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.data.privileged_users.PrivilegedUser
import org.javacord.api.event.server.ServerJoinEvent
import org.javacord.api.listener.server.ServerJoinListener

class BotJoinGuildListener(private val bot: JorstadBot) : ServerJoinListener {
    override fun onServerJoin(event: ServerJoinEvent) {
        bot.data.privilegedUser.addPrivilegedUser(
            event.server.id,
            event.server.ownerId,
            PrivilegedUser.Level.OP
        )
    }
}