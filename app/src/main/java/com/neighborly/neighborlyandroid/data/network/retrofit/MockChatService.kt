package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.network.dto.chat.ConvoResponse
import com.neighborly.neighborlyandroid.data.network.dto.chat.MessageResponse
import retrofit2.Response

class MockChatService():ChatApi{
    override suspend fun getChats(): Response<List<ConvoResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMessages(
        chatId: Long,
        howFarBack: Int
    ): Response<List<MessageResponse>> {
        TODO("Not yet implemented")
    }
}