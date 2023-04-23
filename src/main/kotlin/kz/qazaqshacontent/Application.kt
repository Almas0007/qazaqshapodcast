package kz.qazaqshacontent

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kz.qazaqshacontent.features.channel.configureRoutingChannels
import kz.qazaqshacontent.features.video.configureVideosRouting
import kz.qazaqshacontent.plugins.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/qazaqshadb",
        driver = "org.postgresql.Driver",
        "postgres",
        "Utepov2020"
    )
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureLoginRouting()
    configureRegisterRouting()
    configureSerialization()
    configureRouting()
    configureRoutingChannels()
    configureVideosRouting()

}