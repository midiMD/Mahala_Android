package com.neighborly.neighborlyandroid.data.network.websocket
import android.util.Log
import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import com.neighborly.neighborlyandroid.data.network.RetryPolicy
import com.neighborly.neighborlyandroid.data.network.dto.NetworkResource
import com.neighborly.neighborlyandroid.data.network.dto.chat.ReceivedSocketMessage
import com.neighborly.neighborlyandroid.data.network.dto.chat.RoomDto
import com.neighborly.neighborlyandroid.data.network.dto.chat.SendSocketMessage
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.websocket.send
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.json.JSONObject

//class ChatSocketServiceImpl(private val tokenDataStore: TokenDataStore): ChatSocketService {
//    private val client = HttpClient(CIO) {
//        install(WebSockets)
//    }
//    private var socket: DefaultClientWebSocketSession? = null
//    var isConnected:Boolean = false
//    var isAuthenticated:Boolean = false
//
//    override suspend fun initSession(): Resource<Unit> {
//        // Connect to WS server
//        return try {
//            // open the socket session with the server for this user
//            socket = client.webSocketSession {
//                url(ChatSocketService.Endpoints.ChatSocket.url)
//            }
//
//            if(socket?.isActive == true) {
//                isConnected = true
//                Resource.Success(Unit)
//
//            } else
//                isConnected=false
//                isAuthenticated=false
//                Resource.Error("Couldn't establish a connection.")
//
//        } catch(e: Exception) {
//            isConnected=false
//            isAuthenticated=false
//            e.printStackTrace()
//            Resource.Error(e.localizedMessage ?: "Unknown error")
//        }
//    }
//
//    override suspend fun sendMessage(roomId: Int, content: String) {
//        try {
//            socket?.outgoing?.send(Frame.Text(SendSocketMessage.NewMessage(content = content,roomId = roomId).toString()))
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    override suspend fun authenticate():Resource<Unit> {
//        try {
//            socket?.outgoing?.send(Frame.Text(SendSocketMessage.Authenticate(token = tokenDataStore.getToken().toString()).toString()))
//            // wait for confirmation
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Resource.Error<Unit>(
//                e.localizedMessage ?: "Unknown error"
//            )
//        }
//    }
//
//    override suspend fun createOrGetRoom(otherId: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getMessagesInRoom(roomId: Int) {
//        TODO("Not yet implemented")
//    }
//
//
//    // Setup flow for incoming messages
//    override fun observeMessages(): Flow<ReceivedSocketMessage> {
//
//        return try {
//            socket
//                ?.incoming
//                ?.receiveAsFlow()
//                ?.filter {
//                    it is Frame.Text
//                }
//                ?.map {
//                    val json = (it as? Frame.Text)?.readText() ?: ""
//                    val receivedMessageDto = Json.decodeFromString<ReceivedSocketMessage>(json)
//                    // Check if it is Authentication related and update isAuthenticated state accordingly
//                    Log.i("logs","Received message from Chat Websocket: $receivedMessageDto")
//                    receivedMessageDto
//                }
//                ?: flow { /* return empty flow */ }
//        } catch(e: Exception) {
//            e.printStackTrace()
//            flow {  }
//        }
//    }
//
//    override suspend fun closeSession() {
//        socket?.cancel()
//    }
//}
class ChatSocketServiceImpl(private val tokenDataStore: TokenDataStore) : ChatSocketService {
    private val client = HttpClient(CIO) {
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }

    override fun authenticatedWebSocketSession(
        retry: RetryPolicy,
    ): Deferred<NetworkResource<DefaultClientWebSocketSession>> =
        client.async(start = CoroutineStart.LAZY) {
            try {
                val session = webSocketSession(retry)
                    .apply { authenticate(retry) }
                NetworkResource.Success(session)
            } catch (e: Exception) {
                currentCoroutineContext().ensureActive()
                NetworkResource.Error(e.localizedMessage ?: "Unknown error")
            }
        }

    private suspend fun webSocketSession(retry: RetryPolicy): DefaultClientWebSocketSession =
        retry {
            client.webSocketSession {
                url(ChatSocketService.Endpoints.ChatSocket.url)
            }
        }

    private suspend fun DefaultClientWebSocketSession.authenticate(retry: RetryPolicy) =
        retry {
            val message = SendSocketMessage.Authenticate(token = tokenDataStore.getToken().toString())
                .toString()

            send(message)
            val authResponse = receiveDeserialized<ReceivedSocketMessage>()
            if (authResponse !is ReceivedSocketMessage.AuthenticationSuccess)
                throw RuntimeException("$authResponse")
        }
}

fun DefaultClientWebSocketSession.flow(): Flow<ReceivedSocketMessage> = incoming
    .receiveAsFlow()
    .filterIsInstance<Frame.Text>()
    .mapNotNull {
        try {
            val receivedMessageDto = Json.decodeFromString<ReceivedSocketMessage>(it.readText())
            Log.i("logs", "Received message from Chat Websocket: $receivedMessageDto")
            receivedMessageDto
        } catch (e: Exception) {
            currentCoroutineContext().ensureActive()
            e.printStackTrace()
            null
        }
    }

