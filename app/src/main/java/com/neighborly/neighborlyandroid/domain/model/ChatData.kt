package com.neighborly.neighborlyandroid.domain.model

// Exposed to the UI
sealed class ChatEvent(){
    data object Connected:ChatEvent()
    class NewMessage(roomId:Int, content:String):ChatEvent()
    class Error(message:String?):ChatEvent()
    class RoomMessages(roomId:Int, messages:List<Message>):ChatEvent()
}


data class RoomInfo(
    val id:Int,
    val otherUserName:String,
    val lastMessage:Message,
    val hasUnread:Boolean
)
data class Message(
    val id:Int,
    val roomId:Int,
    val senderId:Int,
    val content:String,
    val timestamp:String
)
