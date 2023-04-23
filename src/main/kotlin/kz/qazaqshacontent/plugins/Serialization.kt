package kz.qazaqshacontent.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.buildJsonObject


fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}