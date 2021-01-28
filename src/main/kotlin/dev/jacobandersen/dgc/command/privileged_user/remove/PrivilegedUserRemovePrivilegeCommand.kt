package dev.jacobandersen.dgc.command.privileged_user.remove

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.TerminalSubcommand
import dev.jacobandersen.dgc.ext.*

class PrivilegedUserRemovePrivilegeCommand(private val bot: DgcBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("privilege", "priv"))
            .argument(StringArgument.of("user"))
            .argument(StringArgument.of("privileges"))
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

                db.updatePrivilegedUser(guild.id, target.id) {
                    it.removeAll(handler.resolvePrivilegesFromArgument())
                    return@updatePrivilegedUser it
                }

                handler.sender.sendSuccessMessage("The specified user has had the specified privileges removed.")
            }
    }
}