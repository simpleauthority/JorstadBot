package dev.jacobandersen.jorstad.command.role._default

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.BaseSubcommand
import dev.jacobandersen.jorstad.command.api.TerminalSubcommand
import dev.jacobandersen.jorstad.ext.getGuildFromCtx

class RoleDefaultCommand(private val bot: JorstadBot) : BaseSubcommand, TerminalSubcommand {
    override fun children(builder: Command.Builder<JavacordCommandSender>): List<Command.Builder<JavacordCommandSender>> {
        val base = terminal(builder)
        val defaultSet = RoleDefaultSetCommand(bot).terminal(base)

        return listOf(base, defaultSet)
    }

    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("default"))
            .handler { handler ->
                val guild = bot.discord.api.getGuildFromCtx(handler) ?: return@handler
                var defaultRole = bot.config.getDefaultUserRole(guild.id)
                defaultRole = if (defaultRole.isEmpty()) { "not set" } else { defaultRole }

                handler.sender.sendMessage("The default role for this server is $defaultRole.")
            }
    }
}