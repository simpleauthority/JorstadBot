package dev.jacobandersen.jorstad.command.config

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.TerminalSubcommand
import dev.jacobandersen.jorstad.ext.resolveGuildFromContext

class ConfigSetCommand(private val bot: JorstadBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("set"))
            .argument(StringArgument.of("node"))
            .argument(StringArgument.greedy("value"))
            .handler { handler ->
                val guild = bot.discord.api.resolveGuildFromContext(handler) ?: return@handler
                val value = handler.get<String>("value")

                bot.config.setValueAtPath(String::class, guild.id, handler.get("node"), value)
                handler.sender.sendMessage("Config value is now set to: $value")
            }
    }
}