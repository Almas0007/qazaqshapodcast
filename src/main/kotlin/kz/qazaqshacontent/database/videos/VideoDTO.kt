package kz.qazaqshacontent.database.videos

import kotlinx.serialization.Serializable

@Serializable
class VideoDTO(
    val video_id: Int,
    val title :String,
    val description:String,
    val video_link :String,
    val channel_id :Int
)