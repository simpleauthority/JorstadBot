package dev.jacobandersen.jorstad.command.privileged_user.remove

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.TerminalSubcommand
import dev.jacobandersen.jorstad.data.privileged_users.PrivilegedUser
import dev.jacobandersen.jorstad.ext.doesNotExist
import dev.jacobandersen.jorstad.ext.resolveDiscordUserFromArgument
import dev.jacobandersen.jorstad.ext.resolveGuildFromContext

class PrivilegedUserRemoveUserCommand(private val bot: JorstadBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("user"))
            .argument(StringArgument.of("user"))
            .handler { handler ->
                val guild = bot.discord.api.resolveGuildFromContext(handler) ?: return@handler
                val target = handler.resolveDiscordUserFromArgument(guild) ?: return@handler

                val db = bot.data.privilegedUser
                if (db.doesNotExist(handler.sender, guild.id, target.id)) return@handler
                db.deletePrivilegedUser(guild.id, target.id)

                handler.sender.sendSuccessMessage("The specified user is now unprivileged.")
            }
    }
}