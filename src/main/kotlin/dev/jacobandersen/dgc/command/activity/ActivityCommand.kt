package dev.jacobandersen.dgc.command.activity

import cloud.commandframework.Command
import cloud.commandframework.javacord.JavacordCommandManager
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import dev.jacobandersen.dgc.command.api.BasicCommand
import dev.jacobandersen.dgc.command.api.argument.UserArgument
import org.javacord.api.entity.user.User

class ActivityCommand(private val bot: DgcBot) : BasicCommand() {
    override fun construct(manager: JavacordCommandManager<JavacordCommandSender>): List<Command<JavacordCommandSender>> {
        return listOf(manager.commandBuilder("activity")
            .argument(UserArgument.of(bot, "user"))
            .handler {
                val user = it.get<User>("user")

                val activity = bot.data.activityTracker.findByUserId(user.id)
                if (activity == null) {
                    it.sender.sendErrorMessage("Either that user is not in this server or they have never talked. *Weird*.")
                    return@handler
                }

                it.sender.sendSuccessMessage("I last saw that user on ${activity.lastActivity}. They have a total of ${activity.totalMessages} messages.")
            }
            .build()
        )
    }
}