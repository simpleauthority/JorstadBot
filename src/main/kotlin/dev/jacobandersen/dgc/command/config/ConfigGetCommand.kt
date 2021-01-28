package dev.jacobandersen.dgc.command.config

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.TerminalSubcommand
import dev.jacobandersen.dgc.ext.resolveGuildFromContext

class ConfigGetCommand(private val bot: DgcBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("get"))
            .argument(StringArgument.of("node"))
            .handler { handler ->
                val guild = bot.discord.api.resolveGuildFromContext(handler) ?: return@handler
                val value = bot.config.readValueAtPath(String::class, guild.id, handler.get("node"))
                handler.sender.sendMessage("Config value is currently set to: $value")
            }
    }
}