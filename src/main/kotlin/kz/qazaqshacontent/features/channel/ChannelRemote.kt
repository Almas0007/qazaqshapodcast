package kz.qazaqshacontent.features.channel

import kotlinx.serialization.Serializable


@Serializable
data class ChannelRequest(
    val channel_id: Int
)
@Serializable
data class ChannelAddRequest(
    val title: String,
    val description: String,
    val icon_link: String,
    val background_link: String,
)
@Serializable
data class ChannelResponse(
    val id: Int,
    val title: String,
    val description: String,
    val iconLink: String,
    val backgroundLink: String,
)
