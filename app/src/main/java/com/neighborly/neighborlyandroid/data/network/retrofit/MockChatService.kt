package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.network.dto.chat.RoomResponse
import com.neighborly.neighborlyandroid.data.network.dto.chat.MessageResponse
import retrofit2.Response

class MockChatService():ChatService{

    override suspend fun authenticate(authToken: String): List<RoomResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun connect() {
        TODO("Not yet implemented")
    }

    override suspend fun disconnect() {
        TODO("Not yet implemented")
    }

    override fun isConnected(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(roomId: Long, message: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRooms(): List<RoomResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getMessages(roomId: Long): List<MessageResponse> {
        TODO("Not yet implemented")
    }
}