package kz.qazaqshacontent.database.channels

import kotlinx.serialization.Serializable
import kz.qazaqshacontent.database.videos.VideoDTO

@Serializable
data class ChannelVideoDTO(
    val channel_id: Int,
    val title: String,
    val description: String,
    val icon_link: String,
    val background_link: String,
    val video:List<VideoDTO>
)