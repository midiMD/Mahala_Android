package com.neighborly.neighborlyandroid.data.network.dto.chat

import com.google.gson.annotations.SerializedName

data class ConvoResponse(
    val id:Long, // ID of the chat record in the database
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