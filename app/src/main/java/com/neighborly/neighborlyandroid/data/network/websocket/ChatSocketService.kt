package com.neighborly.neighborlyandroid.data.network.websocket

import com.neighborly.neighborlyandroid.data.network.RetryPolicy
import com.neighborly.neighborlyandroid.data.network.dto.NetworkResource
import com.neighborly.neighborlyandroid.data.network.dto.chat.ReceivedSocketMessage
import com.neighborly.neighborlyandroid.data.network.dto.chat.RoomDto
import io.ktor.client.plugins.websocket.ClientWebSocketSession
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow


interface ChatSocketService {
    fun authenticatedWebSocketSession(retry: RetryPolicy):Deferred<NetworkResource<DefaultClientWebSocketSession>>

    companion object {
        const val BASE_URL = "wss://10.0.2.2:8000:ws/" // remote

    }

    sealed class Endpoints(val url: String) {
        object ChatSocket: Endpoints("$BASE_URL/chat/")
    }
}