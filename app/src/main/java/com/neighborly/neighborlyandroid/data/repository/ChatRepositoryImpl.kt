package com.neighborly.neighborlyandroid.data.repository

import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.data.mock.listOfConversations
import com.neighborly.neighborlyandroid.data.network.retrofit.ChatService
import com.neighborly.neighborlyandroid.domain.model.Conversation
import com.neighborly.neighborlyandroid.domain.repository.ChatRepository

class ChatRepositoryImpl(private val chatService: ChatService): ChatRepository {

    override suspend fun getListOfRooms(): Resource<List<Conversation>> {
        TODO("Not yet implemented")
    }
}