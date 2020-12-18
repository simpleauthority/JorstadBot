package dev.jacobandersen.jorstad.manager

import dev.jacobandersen.jorstad.config.GuildConfig
import dev.jacobandersen.jorstad.util.Log
import org.spongepowered.configurate.BasicConfigurationNode
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.kotlin.objectMapperFactory
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
            Files.copy(javaClass.getResourceAsStream("/config.json"), diskResource.toPath())
        }

        Log.info("Loading configuration file...")
        loader = JacksonConfigurationLoader
            .builder()
            .path(Path.of("config.json"))
            .defaultOptions { options ->
                options.serializers { builder ->
                    builder.registerAnnotatedObjects(objectMapperFactory())
                }
            }
            .build()

        if (!loader.canLoad()) {
            Log.err("Failed to load configuration file! This is a serious problem.")
            throw RuntimeException()
        }

        root = loader.load()
        guildsNode = root.node("guilds")
    }

    /**
     * Checks if the guilds node has a child <guildId>.
     */
    fun hasGuildConfig(guildId: Long): Boolean {
        return root.node("guilds").hasChild(guildId.toString())
    }

    /**
     * Creates a fresh guild config at the guilds.<guildId> path if
     * one does not already exist.
     */
    fun createGuildConfig(guildId: Long) {
        if (hasGuildConfig(guildId)) return
        setGuildConfig(guildId, GuildConfig())
    }

    /**
     * Reads the guild config located at the guilds.<guildId> path if
     * one exists.
     */
    fun readGuildConfig(guildId: Long): GuildConfig? {
        if (!hasGuildConfig(guildId)) return null
        return root.node("guilds").node(guildId.toString()).get(GuildConfig::class)
    }

    /**
     * Updates one or more fields located at the guilds.<guildId> path if
     * a config exists there.
     */
    fun updateGuildConfig(guildId: Long, mutator: (config: GuildConfig) -> GuildConfig) {
        val config = readGuildConfig(guildId) ?: return
        setGuildConfig(guildId, mutator.invoke(config))
    }

    /**
     * Sets the guild config located at the guilds.<guildId> path to null,
     * effectively deleting it from the configuration.
     */
    fun deleteGuildConfig(guildId: Long) {
        setGuildConfig(guildId, null)
    }

    /**
     * Sets the guildConfig located at the guilds.<guildId> path.
     */
    private fun setGuildConfig(guildId: Long, guildConfig: GuildConfig?) {
        root.node("guilds").node(guildId.toString()).set(guildConfig)
        loader.save(root)
    }
}