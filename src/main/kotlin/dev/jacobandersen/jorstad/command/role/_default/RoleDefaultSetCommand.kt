package dev.jacobandersen.jorstad.command.role._default

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.TerminalSubcommand
import dev.jacobandersen.jorstad.ext.getGuildFromCtx
import dev.jacobandersen.jorstad.ext.resolveDiscordRoleFromArgument

class RoleDefaultSetCommand(private val bot: JorstadBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("set"))
            .argument(StringArgument.of("role"))
            .handler { handler ->
                val guild = bot.discord.api.getGuildFromCtx(handler) ?: return@handler
                val role = handler.resolveDiscordRoleFromArgument(guild) ?: return@handler

                bot.config.updateGuildConfig(guild.id) {
                    it.defaultUserRole = role.id.toString()
                    return@updateGuildConfig it
                }

                handler.sender.sendSuccessMessage("Default guild role set to ${role.id}")
            }
    }
}