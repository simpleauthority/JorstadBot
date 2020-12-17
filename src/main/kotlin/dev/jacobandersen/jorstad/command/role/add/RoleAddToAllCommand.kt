package dev.jacobandersen.jorstad.command.role.add

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.TerminalSubcommand
import dev.jacobandersen.jorstad.ext.getGuildFromCtx
import dev.jacobandersen.jorstad.ext.resolveDiscordRoleFromArgument

class RoleAddToAllCommand(private val bot: JorstadBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("all"))
            .argument(StringArgument.of("role"))
            .handler { handler ->
                val guild = bot.discord.api.getGuildFromCtx(handler) ?: return@handler
                val role = handler.resolveDiscordRoleFromArgument(guild) ?: return@handler

                guild.members.forEach { member ->
                    member.addRole(role, "Adding role as requested by privileged user")
                }

                handler.sender.sendSuccessMessage("Added all users to the specified role.")
            }
    }
}