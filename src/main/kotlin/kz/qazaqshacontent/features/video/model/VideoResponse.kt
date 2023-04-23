package kz.qazaqshacontent.features.video.model

import kotlinx.serialization.Serializable
@Serializable
data class VideoResponse (
    val video_id: Int,
    val title :String,
    val description:String,
    val video_link :String,
    val channel_id :Int
)