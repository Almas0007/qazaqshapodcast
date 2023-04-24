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
        "jdbc:postgresql://postgres:6FWdfpSCXIOE34Hvb0GH@containers-us-west-182.railway.app:5641/railway",
        driver = "org.postgresql.Driver",
        "railway",
        "6FWdfpSCXIOE34Hvb0GH"
    )
    embeddedServer(Netty, port = 5641, host = "containers-us-west-182.railway.app", module = Application::module)
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