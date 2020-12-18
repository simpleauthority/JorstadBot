package dev.jacobandersen.jorstad.config

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class GuildConfig(var defaultUserRole: String?, var welcomeMessage: String?) {
    constructor() : this(null, null)
}