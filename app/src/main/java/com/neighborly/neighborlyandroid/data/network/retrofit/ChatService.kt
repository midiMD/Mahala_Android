package com.neighborly.neighborlyandroid.data.network.retrofit

import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import com.neighborly.neighborlyandroid.data.network.dto.chat.MessageDto
import com.neighborly.neighborlyandroid.data.network.dto.chat.RoomDto
import retrofit2.Response

class ChatService(private val tokenDataStore: TokenDataStore):ChatApi {
    // Must include the auth token in all the requests
    private suspend fun getAuthApi(): ChatApi {
        val token = tokenDataStore.getToken() // Directly assign the result
        val retrofit = AuthRetrofitClient(token= token ).getClient()
        val api = retrofit.create(ChatApi::class.java)
        return api
    }

    override suspend fun fetchRooms(): Response<List<RoomDto>> {
        val api = getAuthApi()
        val response = api.fetchRooms()
        return response
    }
    override suspend fun fetchRoomMessages( roomId:Int):Response<List<MessageDto>>{
        return getAuthApi().fetchRoomMessages(roomId)
    }
}