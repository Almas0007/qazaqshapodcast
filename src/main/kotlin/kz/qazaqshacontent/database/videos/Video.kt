package kz.qazaqshacontent.database.videos


import kz.qazaqshacontent.database.channels.Channel
import kz.qazaqshacontent.database.channels.ChannelVideoDTO
import kz.qazaqshacontent.features.video.model.VideoResponse
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


object Video : Table() {
    private val videoId: Column<Int> = Video.integer(name = "video_id")
    private val titleVideo = Video.varchar(name = "title", length = 255)
    private val descriptionVideo = Video.text(name = "description")
    private val videoLinkVideo = Video.varchar(name = "video_link", length =  255)
    private val channelIdVideo = Video.integer(name = "channel_id")




    fun getAllVideoList(): List<VideoResponse> {
        return try {
            transaction {
                Video.selectAll().toList().map {
                        VideoResponse(
                            video_id = it[Video.videoId],
                            title = it[titleVideo],
                            description = it[descriptionVideo],
                            video_link = it[videoLinkVideo],
                            channel_id = it[channelIdVideo]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getVideoByID(id: Int): VideoDTO? {
        return try {
            transaction {
                val videoResponse = Video.select { Video.videoId.eq(id) }.single()
                VideoDTO(
                    video_id = videoResponse[Video.videoId].toInt(),
                    title = videoResponse[titleVideo].toString(),
                    description = videoResponse[descriptionVideo].toString(),
                    video_link = videoResponse[videoLinkVideo].toString(),
                    channel_id = videoResponse[channelIdVideo].toInt()
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    fun getVideoByChannel(channel_id:Int):ChannelVideoDTO?{
        return try {
            transaction {
                val channelResponse = Channel.select { Channel.channelId.eq(channel_id) }.single()
                val videos = mutableListOf<VideoDTO>()

                // создание объектов VideoDTO и добавление их в список
                Video.select { Video.channelIdVideo.eq(channel_id) }.forEach { videoResponse ->
                    val video = VideoDTO(
                        video_id = videoResponse[Video.videoId].toInt(),
                        title = videoResponse[titleVideo].toString(),
                        description = videoResponse[descriptionVideo].toString(),
                        video_link = videoResponse[videoLinkVideo].toString(),
                        channel_id = videoResponse[channelIdVideo].toInt()
                    )
                    videos.add(video)
                }

                ChannelVideoDTO(
                    channel_id = channelResponse[Channel.channelId].toInt(),
                    title = channelResponse[Channel.title].toString(),
                    description = channelResponse[Channel.description].toString(),
                    icon_link = channelResponse[Channel.iconLink].toString(),
                    background_link = channelResponse[Channel.backgroundLink].toString(),
                    video = videos
                )

        }
        }catch (e: Exception) {
            null
        }
    }


}

