package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.network.dto.chat.ConvoResponse
import com.neighborly.neighborlyandroid.data.network.dto.chat.MessageResponse
import retrofit2.Response

interface ChatApi {
    suspend fun getChats(): Response<List<ConvoResponse>>
    suspend fun getMessages(chatId:Long, howFarBack:Int):Response<List<MessageResponse>>
}