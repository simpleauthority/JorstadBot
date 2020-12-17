package dev.jacobandersen.jorstad.command.role._default

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.TerminalSubcommand
import dev.jacobandersen.jorstad.ext.getGuildFromCtx
import dev.jacobandersen.jorstad.ext.resolveDiscordRoleFromArgument

class RoleDefaultUnsetCommand(private val bot: JorstadBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("unset"))
            .handler { handler ->
                val guild = bot.discord.api.getGuildFromCtx(handler) ?: return@handler
                bot.config.setDefaultUserRole(guild.id, "")
                handler.sender.sendSuccessMessage("Default guild role unset.")
            }
    }
}