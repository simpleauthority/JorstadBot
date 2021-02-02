package dev.jacobandersen.dgc.command.api.argument

import cloud.commandframework.arguments.CommandArgument
import cloud.commandframework.context.CommandContext
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.dgc.DgcBot
import io.leangen.geantyref.TypeToken
import org.javacord.api.entity.user.User
import java.util.function.BiFunction

class UserArgument private constructor(
    bot: DgcBot,
    required: Boolean,
    name: String,
    defaultValue: String,
    suggestionsProvider: BiFunction<CommandContext<JavacordCommandSender>, String, List<String>>
) : CommandArgument<JavacordCommandSender, User>(
    required,
    name,
    UserParser(bot),
    defaultValue,
    TypeToken.get(User::class.java),
    suggestionsProvider
) {
    class Builder(private val bot: DgcBot, name: String) :
        CommandArgument.Builder<JavacordCommandSender, User>(User::class.java, name) {
        override fun build(): CommandArgument<JavacordCommandSender, User> {
            return UserArgument(
                bot,
                isRequired,
                name,
                defaultValue,
                suggestionsProvider
            )
        }
    }

    companion object {
        fun of(bot: DgcBot, name: String): CommandArgument<JavacordCommandSender, User> {
            return Builder(bot, name).asRequired().withSuggestionsProvider { _, _ -> emptyList() }.build()
        }
    }
}