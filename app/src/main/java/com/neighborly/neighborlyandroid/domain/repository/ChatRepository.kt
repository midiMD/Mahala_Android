package com.neighborly.neighborlyandroid.domain.repository

import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.data.network.dto.chat.ConvoResponse
import com.neighborly.neighborlyandroid.domain.model.Conversation

interface ChatRepository {
    suspend fun getAllConvos():Resource<List<Conversation>>
}