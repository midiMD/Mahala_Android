package com.neighborly.neighborlyandroid.domain.repository

import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.domain.model.Conversation

interface ChatRepository {
    suspend fun getListOfRooms():Resource<List<Conversation>>

}