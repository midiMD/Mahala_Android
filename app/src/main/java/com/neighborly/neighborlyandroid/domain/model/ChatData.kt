package com.neighborly.neighborlyandroid.domain.model


data class Conversation(
    val id:Long,
    val sender: String,
    val image: Int,
    val amILastSender: Boolean,
    val message: String,
    val time: String,
    val unread: Boolean,
)
data class Message(
    val message: String,
    val time: String,
    val isMe: Boolean
)
