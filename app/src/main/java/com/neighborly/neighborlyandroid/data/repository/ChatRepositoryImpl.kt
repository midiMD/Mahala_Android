package com.neighborly.neighborlyandroid.data.repository

import com.neighborly.neighborlyandroid.data.network.retrofit.ChatApi
import com.neighborly.neighborlyandroid.domain.repository.ChatRepository

class ChatRepositoryImpl(private val chatService: ChatApi): ChatRepository {
}