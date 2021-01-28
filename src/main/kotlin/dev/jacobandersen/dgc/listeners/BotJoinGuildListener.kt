package dev.jacobandersen.dgc.listeners

import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.data.privileged_users.PrivilegedUser
import org.javacord.api.event.server.ServerJoinEvent
import org.javacord.api.listener.server.ServerJoinListener

class BotJoinGuildListener(private val bot: DgcBot) : ServerJoinListener {
    override fun onServerJoin(event: ServerJoinEvent) {
        val guild = event.server

        bot.config.createGuildConfig(guild.id)

        bot.data.privilegedUser.addPrivilegedUser(
            guild.id,
            guild.ownerId,
            listOf(PrivilegedUser.Privilege.ALL)
        )
    }
}