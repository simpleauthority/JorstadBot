package dev.jacobandersen.dgc.module

import dev.jacobandersen.dgc.util.Json
import dev.jacobandersen.dgc.util.TextGenerator

abstract class TextGeneratorModule(fileName: String) {
    private val templates = ArrayList<String>()
    private val components = HashMap<String, List<String>>()

    init {
        val node = Json.deserialize(javaClass.getResourceAsStream("/${fileName}"))

        node.get("templates").forEach { templates.add(it.textValue()) }
        node.get("components").fields().forEach { component ->
            val list = ArrayList<String>()
            component.value.forEach { entry -> list.add(entry.textValue()) }
            components[component.key] = list
        }
    }

    fun generateTargetedAtUser(user: String): String {
        return TextGenerator.generate(templates, components, mapOf(Pair("user", user)))
    }
}
