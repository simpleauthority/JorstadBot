package dev.jacobandersen.jorstad.util

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.InputStream

object Json {
    private val mapper = ObjectMapper()

    fun deserialize(stream: InputStream): JsonNode {
        return mapper.readTree(stream)
    }
}