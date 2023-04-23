package kz.qazaqshacontent.features.channel

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kz.qazaqshacontent.cache.TokenCheck
import kz.qazaqshacontent.database.channels.Channel

class ChannelController(private val call: ApplicationCall) {

    suspend fun addChannel() {
        val receiveRequest = call.receive<ChannelAddRequest>()

        if(receiveRequest.title == null
            || receiveRequest.description == null
            || receiveRequest.icon_link == null
            || receiveRequest.background_link == null){
            call.respond(HttpStatusCode.BadRequest, "Data is not writing")
        }else {
            Channel.insert(receiveRequest)
            call.respond(HttpStatusCode.OK,"Information added")

        }


    }
    suspend fun performChannel() {
        val receiveRequest = call.receive<ChannelRequest>()
        val token = call.request.headers["Bearer-Authorization"]
        val channelDTO = Channel.fetchChannelByID(receiveRequest.channel_id)

        if (TokenCheck.isTokenValid(token.orEmpty())) {
            if(channelDTO == null){
                call.respond(HttpStatusCode.BadRequest,"Id бойынша ақпарат жоқ")
            }else{
                if (channelDTO.id == receiveRequest.channel_id){
                    call.respond(ChannelResponse(
                        id = channelDTO.id,
                        title = channelDTO.title,
                        description = channelDTO.description,
                        iconLink = channelDTO.icon_link,
                        backgroundLink = channelDTO.background_link
                    ))
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Что то не то")
                }
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

//    suspend fun performChannelWithVideo(){
//        val receiveRequest = call.receive<ChannelRequest>()
//        val token = call.request.headers["Bearer-Authorization"]
//        val channelDTO = Channel.fetchChannelByID(receiveRequest.id)
//        val videoDTO = Video.getVideoByChannel(receiveRequest.id)
//
//
//        if (TokenCheck.isTokenValid(token.orEmpty())) {
//
//        }
//    }

}
