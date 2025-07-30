package com.neighborly.neighborlyandroid.domain.repository

import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.domain.model.ChatEvent
import com.neighborly.neighborlyandroid.domain.model.RoomInfo
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun connect():Unit

    suspend fun observe():Flow<ChatEvent>
    suspend fun sendMessage(roomId:Int, content:String)
    suspend fun getListOfRooms():List<RoomInfo>

}