package dev.jacobandersen.jorstad.command.config

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.TerminalSubcommand
import dev.jacobandersen.jorstad.ext.resolveGuildFromContext

class ConfigUnsetCommand(private val bot: JorstadBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("unset"))
            .argument(StringArgument.of("node"))
            .handler { handler ->
                val guild = bot.discord.api.resolveGuildFromContext(handler) ?: return@handler
                bot.config.setValueAtPath(String::class, guild.id, handler.get("node"), null)
                handler.sender.sendMessage("Config value is now unset")
            }
    }
}