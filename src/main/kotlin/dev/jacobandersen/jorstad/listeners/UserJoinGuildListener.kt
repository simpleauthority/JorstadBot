package dev.jacobandersen.jorstad.listeners

import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.ext.getIdFromRoleMention
import org.javacord.api.event.server.member.ServerMemberJoinEvent
import org.javacord.api.listener.server.member.ServerMemberJoinListener

class UserJoinGuildListener(private val bot: JorstadBot) : ServerMemberJoinListener {
    override fun onServerMemberJoin(event: ServerMemberJoinEvent) {
        val config = bot.config.readGuildConfig(event.server.id) ?: return

        val defaultRole = config.defaultUserRole ?: ""
        if (defaultRole.isNotEmpty()) {
            val roleId = defaultRole.getIdFromRoleMention() ?: return
            val role = event.server.getRoleById(roleId).orElse(null) ?: return
            event.user.addRole(role, "Applying default role as set in configuration")
        }

        val welcomeMessage = config.welcomeMessage ?: ""
        if (welcomeMessage.isNotEmpty()) {
            val channel = event.server.systemChannel.orElse(null) ?: return
            channel.sendMessage(welcomeMessage.replace("{user}", event.user.mentionTag))
        }

    }
}