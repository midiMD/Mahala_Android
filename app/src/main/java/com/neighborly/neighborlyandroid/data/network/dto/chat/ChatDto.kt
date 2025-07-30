package com.neighborly.neighborlyandroid.data.network.dto.chat

import com.google.gson.annotations.SerializedName
import com.neighborly.neighborlyandroid.domain.model.Message
import com.neighborly.neighborlyandroid.domain.model.RoomInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class RoomResponse(
    val id:Long, // ID of the Room record in the database
    @SerializedName("sender_name") val senderName:String,
    @SerializedName("am_last") val amILastSender:Boolean, // whether the last message was sent by me
    @SerializedName("last_message") val lastMessage:String, // the last message. This will display in the list of conversations as a subtext
    val time:String,// time of the last message
    val unread:Boolean, // have I opened it?
)

data class MessageResponse(
    val message:String,
    @SerializedName("is_me")val isMe:Boolean,
    val time:String
)
@Serializable
data class ChatUserDto(
    val id:Int,
    @SerializedName("full_name")val fullName:String
)
@Serializable
data class MessageDto(
    val id:Int,
    val content:String,
    @SerializedName("room_id")val roomId:Int,
    val sender:ChatUserDto,
    val timestamp:String,
    val unread:Boolean
)
fun MessageDto.toMessage():Message =
    Message(
        id =id,
        content = content,
        roomId = roomId,
        senderId = sender.id,
        timestamp = timestamp
    )
@Serializable
data class RoomDto(
    val id:Int,
    @SerializedName("other_user") val otherUser:ChatUserDto,
    @SerializedName("last_message") val lastMessage:MessageDto,
    @SerializedName("unread_messages") val hasUnreadMessages:Boolean
)
fun RoomDto.toRoomInfo():RoomInfo =
    RoomInfo(
        id = id,
        otherUserName = otherUser.fullName,
        lastMessage = lastMessage.toMessage(),
        hasUnread = hasUnreadMessages
    )

@Serializable
sealed class ReceivedSocketMessage {
    abstract val type: String
    @Serializable
    @SerialName("new_message")
    data class NewMessage(
        val message: MessageDto,
        override val type: String = "new_message"
    ) : ReceivedSocketMessage()
    @Serializable
    @SerialName("authentication_successful")
    data class AuthenticationSuccess(
        val rooms: List<RoomDto>,
        override val type: String = "authentication_successful"
    ) : ReceivedSocketMessage()
    @Serializable
    @SerialName("authentication_failed")
    data class AuthenticationFail(
        override val type: String = "authentication_failed"
    ) : ReceivedSocketMessage()
    @Serializable
    @SerialName("message_success")
    data class MessageSentSuccess(
        override val type:String= "message_success",
        @SerializedName("room_id") val roomId:Int
    ):ReceivedSocketMessage()
    @Serializable
    @SerialName("pong")
    data class
//    @Serializable
//    @SerialName("room_created")
//    data class RoomCreated(
//        override val type:String= "room_created",
//        @SerializedName("room_id") val roomId:Int
//    ):ReceivedSocketMessage()
//    @Serializable
//    @SerialName("room_data")
//    data class RoomData(
//        override val type:String= "room_data",
//        @SerializedName("room_id") val roomId:Int,
//        val messages:List<MessageDto>
//    ):ReceivedSocketMessage()
//    @Serializable
//    @SerialName("rooms")
//    data class Rooms(
//        override  val type:String = "rooms",
//        val rooms:List<RoomDto>
//    )
//    @Serializable
//    @SerialName("joined_room")
//    data class JoinedRoom(
//        override val type:String= "joined_room",
//        @SerializedName("room_id") val roomId:Int,
//    ):ReceivedSocketMessage()

    data class Error(
        override val type:String = "error",
        val message:String?
    ):ReceivedSocketMessage()

}

@Serializable
sealed class SendSocketMessage {
    abstract val action: String
    @Serializable
    @SerialName("send_message")
    data class NewMessage(
        val content: String,
        override val action: String = "send_message",
        val roomId:Int
    ) : SendSocketMessage()
    @Serializable
    @SerialName("authenticate")
    data class Authenticate(
        override val action: String = "authenticate",
        val token:String
    ) : SendSocketMessage()
    @Serializable
    @SerialName("ping")
    data class Ping(
        override val action: String = "ping",
        val timestamp: Long = System.currentTimeMillis()
    ) : SendSocketMessage()
//    @Serializable
//    @SerialName("create_or_get_room")
//    data class CreateOrGetRoom(
//        override val action: String = "create_or_get_room",
//        val otherId:Int
//    ) : SendSocketMessage()
//    @Serializable
//    @SerialName("fetch_messages")
//    data class FetchRoomMessages(
//        override val action: String = "fetch_messages",
//        val roomId:Int
//    ) : SendSocketMessage()
//    @Serializable
//    @SerialName("get_rooms")
//    data class FetchRooms(
//        override  val action:String = "fetch_rooms"
//    ):SendSocketMessage()

}

