package kz.qazaqshacontent.database.channels


import kz.qazaqshacontent.features.channel.ChannelAddRequest
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Channel : Table() {
    val channelId = Channel.integer("channel_id")
    val title = Channel.varchar("title",255)
    val description = Channel.text("description")
    val iconLink = Channel.varchar("icon_link", 255)
    val backgroundLink = Channel.varchar("background_link", 255)

    fun insert(channelDTO: ChannelAddRequest){
        transaction {
            Channel.insert{
                it[title] = channelDTO.title
                it[description] = channelDTO.description
                it[iconLink] = channelDTO.icon_link
                it[backgroundLink] = channelDTO.background_link
            }
        }
    }

    fun fetchChannelByID(id:Int):ChannelDTO?{
        return try {
            transaction {
                val channelResponse = Channel.select { Channel.channelId.eq(id)}.single()
                ChannelDTO(
                    id = channelResponse[Channel.channelId].toInt(),
                    title = channelResponse[title].toString(),
                    description = channelResponse[description].toString(),
                    icon_link = channelResponse[iconLink].toString(),
                    background_link = channelResponse[backgroundLink].toString()
                )
            }
        }catch (e:Exception){
            null
        }
    }

}

