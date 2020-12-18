package dev.jacobandersen.jorstad.command.privileged_user.add

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.TerminalSubcommand
import dev.jacobandersen.jorstad.ext.*

class PrivilegedUserAddUserCommand(private val bot: JorstadBot) : TerminalSubcommand {
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