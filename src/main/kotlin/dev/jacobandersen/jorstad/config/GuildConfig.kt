package dev.jacobandersen.jorstad.config

import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.ObjectMapper

@ConfigSerializable
class GuildConfig {
    var defaultUserRole: String? = null

    fun saveTo(node: ConfigurationNode) {
        mapper.save(this, node)
    }

    companion object {
        private val mapper = ObjectMapper.factory().get(GuildConfig::class.java)

        fun loadFrom(node: ConfigurationNode): GuildConfig {
            return mapper.load(node)
        }
    }
}