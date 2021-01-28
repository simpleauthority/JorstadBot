package dev.jacobandersen.dgc.command.privileged_user.remove

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.TerminalSubcommand
import dev.jacobandersen.dgc.ext.*

class PrivilegedUserRemoveUserCommand(private val bot: DgcBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("user"))
            .argument(StringArgument.of("user"))
            .handler { handler ->
                val guild = bot.discord.api.resolveGuildFromContext(handler) ?: return@handler
                val target = handler.resolveDiscordUserFromArgument(guild) ?: return@handler

                if (handler.isGuildOwner(
                        target,
                        "You can't remove the server owner's privileges. That is when Bad Things:tm: occur."
                    )
                ) return@handler

                val db = bot.data.privilegedUser
                if (db.doesNotExist(handler.sender, guild.id, target.id)) return@handler
                db.deletePrivilegedUser(guild.id, target.id)

                handler.sender.sendSuccessMessage("The specified user is now unprivileged.")
            }
    }
}