package dev.jacobandersen.dgc.command.privileged_user.add

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.TerminalSubcommand
import dev.jacobandersen.dgc.ext.*

class PrivilegedUserAddUserCommand(private val bot: DgcBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("user"))
            .argument(StringArgument.of("user"))
            .argument(StringArgument.of("privileges"))
            .handler { handler ->
                val guild = bot.discord.api.resolveGuildFromContext(handler) ?: return@handler
                val target = handler.resolveDiscordUserFromArgument(guild) ?: return@handler
                val privileges = handler.resolvePrivilegesFromArgument()

                val db = bot.data.privilegedUser
                if (db.alreadyExists(handler.sender, guild.id, target.id)) return@handler
                db.addPrivilegedUser(guild.id, target.id, privileges)

                handler.sender.sendSuccessMessage("The specified user is now privileged with the specified privileges.")
            }
    }
}