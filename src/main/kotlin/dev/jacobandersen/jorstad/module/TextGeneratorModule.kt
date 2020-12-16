package dev.jacobandersen.jorstad.module

import dev.jacobandersen.jorstad.util.Json
import dev.jacobandersen.jorstad.util.TextGenerator

abstract class TextGeneratorModule(fileName: String) {
    private val templates = ArrayList<String>()
    private val components = HashMap<String, List<String>>()

    init {
        val kill = Json.deserialize(javaClass.getResourceAsStream("/${fileName}"))

        kill.get("templates").forEach { templates.add(it.textValue()) }
        kill.get("components").fields().forEach { component ->
            val list = ArrayList<String>()
            component.value.forEach { entry -> list.add(entry.textValue()) }
            components[component.key] = list
        }
    }

    fun generateTargetedAtUser(user: String): String {
        return TextGenerator.generate(templates, components, mapOf(Pair("user", user)))
    }
}