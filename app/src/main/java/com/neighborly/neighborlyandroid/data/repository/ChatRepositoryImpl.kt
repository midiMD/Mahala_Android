package com.neighborly.neighborlyandroid.data.repository

import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.data.mock.listOfConversations
import com.neighborly.neighborlyandroid.data.network.retrofit.ChatApi
import com.neighborly.neighborlyandroid.domain.model.Conversation
import com.neighborly.neighborlyandroid.domain.repository.ChatRepository

class ChatRepositoryImpl(private val chatService: ChatApi): ChatRepository {
    override suspend fun getAllConvos(): Resource<List<Conversation>> {
        return Resource.Success(listOfConversations)
    }
}