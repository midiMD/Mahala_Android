package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.network.dto.chat.MessageResponse
import com.neighborly.neighborlyandroid.data.network.dto.chat.RoomResponse

// ChatService interface
interface ChatService {
    suspend fun authenticate(authToken:String):List<RoomResponse>
    suspend fun connect()
    suspend fun disconnect()
    fun isConnected(): Boolean
    suspend fun sendMessage(roomId:Long,message:String)
    suspend fun getAllRooms():List<RoomResponse>
    suspend fun getMessages(roomId:Long):List<MessageResponse>
}
