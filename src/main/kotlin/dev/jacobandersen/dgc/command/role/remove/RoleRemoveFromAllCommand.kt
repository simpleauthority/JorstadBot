package dev.jacobandersen.dgc.command.role.remove

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.TerminalSubcommand
import dev.jacobandersen.dgc.ext.resolveDiscordRoleFromArgument
import dev.jacobandersen.dgc.ext.resolveGuildFromContext

class RoleRemoveFromAllCommand(private val bot: DgcBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("all"))
            .argument(StringArgument.of("role"))
            .handler { handler ->
                val guild = bot.discord.api.resolveGuildFromContext(handler) ?: return@handler
                val role = handler.resolveDiscordRoleFromArgument(guild) ?: return@handler

                guild.members.forEach { member ->
                    if (member.isBot) return@forEach
                    member.removeRole(role, "Removing role as requested by privileged user")
                }

                handler.sender.sendSuccessMessage("Removed all users from the specified role.")
            }
    }
}