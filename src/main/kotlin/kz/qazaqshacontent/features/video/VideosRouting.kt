package kz.qazaqshacontent.features.video

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureVideosRouting() {
    routing {
        get("/all-videos") {
            VideoController(call).performAllVideo()
        }

        get("/video") {
            VideoController(call).performChannelById()
        }

        get("/channel-video"){
            VideoController(call).performChannelWithVideo()
        }

    }

}