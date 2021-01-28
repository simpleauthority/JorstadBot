package dev.jacobandersen.dgc.ext

import cloud.commandframework.context.CommandContext
import cloud.commandframework.javacord.sender.JavacordCommandSender
import org.javacord.api.DiscordApi
import org.javacord.api.entity.server.Server

fun DiscordApi.resolveGuildFromContext(ctx: CommandContext<JavacordCommandSender>): Server? {
    return resolveGuildFromSender(ctx.sender)
}

fun DiscordApi.resolveGuildFromSender(sender: JavacordCommandSender): Server? {
    val guildId = sender.event.server.orElse(null)?.id ?: return null
    return this.getServerById(guildId).orElse(null) ?: return null
}