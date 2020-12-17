package dev.jacobandersen.jorstad.ext

import cloud.commandframework.context.CommandContext
import cloud.commandframework.javacord.sender.JavacordCommandSender
import org.javacord.api.DiscordApi
import org.javacord.api.entity.server.Server

fun DiscordApi.getGuildFromCtx(ctx: CommandContext<JavacordCommandSender>): Server? {
    val guildId = ctx.sender.event.server.orElse(null)?.id ?: return null
    return this.getServerById(guildId).orElse(null) ?: return null
}