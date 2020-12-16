package dev.jacobandersen.jorstad.data.textcommands

import dev.jacobandersen.jorstad.data.DataManager

class TextCommandDam(manager: DataManager) {
    private val dao = manager.jdbi.onDemand(TextCommandDao::class.java)

    fun createContainer() {
        dao.createContainer()
    }

    fun nameExists(name: String): Boolean {
        return dao.nameExists(name)
    }

    fun addTextCommand(name: String, output: String): TextCommand {
        dao.addTextCommand(name, output)
        return findTextCommand(name)
    }

    fun findTextCommand(name: String): TextCommand {
        return dao.findTextCommand(name)
    }

    fun findTextCommands(): List<TextCommand> {
        return dao.findTextCommands()
    }

    fun deleteTextCommand(name: String) {
        dao.deleteTextCommand(name)
    }
}