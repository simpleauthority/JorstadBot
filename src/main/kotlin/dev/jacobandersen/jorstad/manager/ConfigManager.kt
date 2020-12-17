package dev.jacobandersen.jorstad.manager

import dev.jacobandersen.jorstad.util.Log
import org.spongepowered.configurate.BasicConfigurationNode
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader
import java.io.File
import java.lang.RuntimeException
import java.nio.file.Files
import java.nio.file.Path

class ConfigManager {
    private val loader: JacksonConfigurationLoader
    private val root: BasicConfigurationNode
    private val guildsNode: BasicConfigurationNode

    init {
        Log.info("Checking configuration state...")
        val diskResource = File("config.json")
        if (!diskResource.exists()) {
            Log.info("Copying default configuration file...")
            Files.copy(
                javaClass.getResourceAsStream("/config.json"),
                diskResource.toPath()
            )
            Log.info("Default configuration copied.")
        }

        Log.info("Loading configuration file...")
        loader = JacksonConfigurationLoader
            .builder()
            .path(Path.of("config.json"))
            .build()

        if (!loader.canLoad()) {
            Log.err("Failed to load configuration file! This is a serious problem.")
            throw RuntimeException()
        }

        root = loader.load()
        guildsNode = root.node("guilds")
        Log.info("Configuration loaded.")
    }

    fun getDefaultUserRole(guildId: Long): String {
        return guildsNode.node(guildId).node("defaultUserRole").getString("")
    }

    fun setDefaultUserRole(guildId: Long, defaultUserRole: String) {
        guildsNode.node(guildId).node("defaultUserRole").set(defaultUserRole)
        save()
    }

    fun deleteGuild(guildId: Long) {
        guildsNode.node(guildId).set(null)
        save()
    }

    private fun save() {
        loader.save(root)
    }
}