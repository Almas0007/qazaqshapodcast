package kz.qazaqshacontent.features.channel

import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureRoutingChannels() {
    routing {
        get("/channels") {
            val channel = ChannelController(call)
            channel.performChannel()
        }
        post("/channels") {
            val channel = ChannelController(call)
            channel.addChannel()
        }

    }

}
