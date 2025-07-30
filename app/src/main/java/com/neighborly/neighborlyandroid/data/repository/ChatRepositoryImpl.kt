package com.neighborly.neighborlyandroid.data.repository

import android.util.Log
import coil3.network.HttpException
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.data.network.SimpleRetryPolicy
import com.neighborly.neighborlyandroid.data.network.dto.NetworkResource
import com.neighborly.neighborlyandroid.data.network.dto.chat.ReceivedSocketMessage
import com.neighborly.neighborlyandroid.data.network.dto.chat.SendSocketMessage
import com.neighborly.neighborlyandroid.data.network.dto.chat.toMessage
import com.neighborly.neighborlyandroid.data.network.dto.chat.toRoomInfo
import com.neighborly.neighborlyandroid.data.network.dto.inventory.toInventoryItem
import com.neighborly.neighborlyandroid.data.network.dto.inventory.toInventoryItemDetail
import com.neighborly.neighborlyandroid.data.network.retrofit.ChatApi
import com.neighborly.neighborlyandroid.data.network.websocket.ChatSocketService
import com.neighborly.neighborlyandroid.data.network.websocket.flow
import com.neighborly.neighborlyandroid.domain.model.ChatEvent
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.InventoryItemDetail
import com.neighborly.neighborlyandroid.domain.model.RoomInfo
import com.neighborly.neighborlyandroid.domain.repository.ChatRepository
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.websocket.send
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import kotlinx.io.IOException

class ChatRepositoryImpl(private val socketService: ChatSocketService, private val api:ChatApi): ChatRepository {

    private val _chatConnectionState = MutableStateFlow<ChatConnectionState>(ChatConnectionState.Success)
    val chatConnectionState = _chatConnectionState.asStateFlow()
    private val _rooms = MutableStateFlow<List<RoomInfo>>(emptyList())
    private var _session: DefaultClientWebSocketSession? = null

    override suspend fun connect(){

        val result = socketService.authenticatedWebSocketSession(SimpleRetryPolicy(3)).await()

        when(result) {
            is NetworkResource.Error -> {
                _session = null
                _chatConnectionState.value = ChatConnectionState.Error("Failed to connect to the chat server")
            }
            is NetworkResource.Success -> {
                _session  = result.data
                _chatConnectionState.value = ChatConnectionState.Success
            }
        }
    }

    override suspend fun observe():Flow<ChatEvent> =
        _session?.flow()
            ?.mapNotNull {
                when (it){
                    is ReceivedSocketMessage.NewMessage -> {
                        Log.i("logs","Received new message from ${it.message.sender.fullName} : ${it.message.content} ")
                        ChatEvent.NewMessage(content = it.message.content, roomId = it.message.roomId)
                    }

                    is ReceivedSocketMessage.Error -> {
                        Log.d("logs","Chat websocket error: ${it.message}")
                        ChatEvent.Error("Error occurred")
                    }

                    is ReceivedSocketMessage.JoinedRoom -> {
                        Log.i("logs","Added to new room ${it.roomId}")
                        null
                    }

                    is ReceivedSocketMessage.MessageSentSuccess -> {
                        Log.i("logs","Message to room ${it.roomId} sent succesfully")
                        null
                    }

                    is ReceivedSocketMessage.RoomCreated -> {
                        Log.i("logs","Room ${it.roomId} created successfully ")
                        null
                    }

                    is ReceivedSocketMessage.RoomData -> {
                        Log.d("logs","Messages in room ${it.roomId} : ${it.messages.joinToString { messageDto->messageDto.content }}")
                        ChatEvent.RoomMessages(roomId = it.roomId, messages = it.messages.map { messageDto->messageDto.toMessage() })
                    }
                    is ReceivedSocketMessage.AuthenticationSuccess,
                    is ReceivedSocketMessage.AuthenticationFail->{
                        null
                    }
                }
            } ?: emptyFlow()
    override suspend fun sendMessage(roomId:Int, content:String){

        _session?.send(SendSocketMessage.NewMessage(roomId=roomId, content = content).toString())
        Log.i("logs", "Message sent")
    }

    override suspend fun fetchRooms(): Resource<List<RoomInfo>> {
        return withContext(Dispatchers.IO) {
            //Network and local storage IO operations should be done in IO Context
            try {
                val response = api.fetchRooms()

                if (response.isSuccessful) {
                    val rooms:List<RoomInfo> = response.body()!!.map { roomDto -> roomDto.toRoomInfo() }
                    Resource.Success(data = rooms)
                } else {
                    Log.e("logs","Error fetching chat rooms : ${response.errorBody()}")
                    when (response.code()){
                        500 -> Resource.Error.ServerError()
                        401,403 -> Resource.Error.AccessDenied()
                        else -> Resource.Error.ServerError()
                    }
                }

            }catch (e: Exception) {
                Log.d("logs", "Chat Repository Fetch Rooms error: $e")
                when (e) {
                    is HttpException -> Resource.Error.ClientError()
                    is IOException -> Resource.Error.NetworkError()
                    else -> Resource.Error.ServerError()
                }
            }
        }
    }
}

sealed class ChatConnectionState {
    data object Success:ChatConnectionState()
    data class Error(val message:String?):ChatConnectionState()
}
