package com.neighborly.neighborlyandroid.data.network.retrofit
import com.neighborly.neighborlyandroid.data.network.dto.chat.MessageDto
import com.neighborly.neighborlyandroid.data.network.dto.chat.RoomDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ChatApi{
    @GET("/chat/rooms")
    suspend fun fetchRooms(
    ): Response<List<RoomDto>>
    @GET("/chat/messages")
    suspend fun fetchRoomMessages(
        @Query("room_id") roomId:Int
    ): Response<List<MessageDto>>
}

