package dev.jacobandersen.dgc.data.text_commands

import dev.jacobandersen.dgc.manager.DataManager

class TextCommandDam(manager: DataManager) {
    private val dao = manager.jdbi.onDemand(TextCommandDao::class.java)

    fun createContainer() {
        dao.createContainer()
    }

    fun textCommandExists(guildId: Long, name: String): Boolean {
        return dao.textCommandExists(guildId, name)
    }

    fun addTextCommand(guildId: Long, name: String, output: String) {
        dao.addTextCommand(guildId, name, output)
    }

    fun findTextCommand(guildId: Long, name: String): TextCommand? {
        return dao.findTextCommand(guildId, name)
    }

    fun findTextCommands(guildId: Long): List<TextCommand> {
        return dao.findTextCommands(guildId)
    }

    fun deleteTextCommand(guildId: Long, name: String) {
        dao.deleteTextCommand(guildId, name)
    }
}