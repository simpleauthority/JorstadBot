package dev.jacobandersen.dgc.command.privileged_user.add

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.TerminalSubcommand
import dev.jacobandersen.dgc.ext.*

class PrivilegedUserAddPrivilegeCommand(private val bot: DgcBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("privilege", "priv"))
            .argument(StringArgument.of("user"))
            .argument(StringArgument.of("privileges"))
            .handler { handler ->
                val guild = bot.discord.api.resolveGuildFromContext(handler) ?: return@handler
                val target = handler.resolveDiscordUserFromArgument(guild) ?: return@handler

                val db = bot.data.privilegedUser
                if (db.doesNotExist(handler.sender, guild.id, target.id)) return@handler

                val privileges = handler.resolvePrivilegesFromArgument();
                if (privileges.isEmpty()) {
                    handler.sender.sendErrorMessage("I could not find any valid privileges in the provided list.")
                    return@handler
                }

                db.updatePrivilegedUser(guild.id, target.id) {
                    it.addAll(privileges)
                    return@updatePrivilegedUser it
                }

                handler.sender.sendSuccessMessage("The specified user has been given the specified privileges.")
            }
    }
}