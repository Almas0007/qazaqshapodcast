package kz.qazaqshacontent.features.video

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kz.qazaqshacontent.cache.TokenCheck
import kz.qazaqshacontent.database.videos.Video
import kz.qazaqshacontent.features.channel.ChannelRequest
import kz.qazaqshacontent.features.video.model.VideoIdRequest
import kz.qazaqshacontent.features.video.model.VideoResponse

class VideoController(private val call: ApplicationCall) {

    suspend fun performAllVideo() {
        val token = call.request.headers["Bearer-Authorization"]
        val videoDTO = Video.getAllVideoList()

        if (TokenCheck.isTokenValid(token.orEmpty())) {
            call.respond(videoDTO)
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun performChannelById() {
        val receiveRequest = call.receive<VideoIdRequest>()
        val token = call.request.headers["Bearer-Authorization"]
        val videoDTO = Video.getVideoByID(receiveRequest.video_id)

        if (TokenCheck.isTokenValid(token.orEmpty())) {
            if (videoDTO == null) {
                call.respond(HttpStatusCode.BadRequest, "Id бойынша ақпарат жоқ")
            } else {
                if (videoDTO.video_id == receiveRequest.video_id) {
                    call.respond(VideoResponse(
                            video_id = videoDTO.video_id,
                            title = videoDTO.title,
                            description = videoDTO.description,
                            video_link = videoDTO.video_link,
                            channel_id = videoDTO.channel_id
                        ))
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Что то не то")
                }
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun performChannelWithVideo(){
        val receiveRequest = call.receive<ChannelRequest>()
        val token = call.request.headers["Bearer-Authorization"]
        val channelVideoDTO = Video.getVideoByChannel(receiveRequest.channel_id)

        if (TokenCheck.isTokenValid(token.orEmpty())) {
            if (channelVideoDTO == null) {
                call.respond(HttpStatusCode.BadRequest, "Id бойынша ақпарат, арна жоқ")
            } else {
                if (channelVideoDTO.channel_id == receiveRequest.channel_id) {
                    call.respond(channelVideoDTO)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Что то не то")
                }
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }

    }

}