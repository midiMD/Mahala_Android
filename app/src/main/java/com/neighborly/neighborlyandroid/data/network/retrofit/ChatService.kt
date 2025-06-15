package com.neighborly.neighborlyandroid.data.network.retrofit

import com.neighborly.neighborlyandroid.data.network.dto.chat.MessageResponse
import com.neighborly.neighborlyandroid.data.network.dto.chat.RoomResponse


// Implementation of ChatService
// format of error response from websocket server is {
//                'type': 'error',
//                'message': errorMessage,
//
//            }
class ChatServiceImpl : ChatService {
    private var connected = false
    private var isAuthenticated = false
    override suspend fun authenticate(authToken:String): List<RoomResponse> {
        val actionKeyword = "authenticate"
        if (connected){
            //format of message to send is {"action":actionKeyword, "token" = authToken}
            // format of succesful response is {
            //                    'type': 'authentication_successful',
            //                    'rooms': List<RoomResponse>
            //                }
            // format of error response is {"type":"authentication_failed"}
            isAuthenticated = true
        }else{
            // try connect again
        }

        TODO("Not yet implemented")
    }

    override suspend fun connect() {
        if (!connected) {
            // Implement your WebSocket connection logic here
            connected = true
        }
    }

    override suspend fun disconnect() {
        if (connected) {
            // Implement your WebSocket disconnection logic here
            connected = false
        }
    }

    override fun isConnected(): Boolean = connected
    override suspend fun sendMessage(roomId: Long, message: String) {
        val actionKeyword = "send_message"
        //format of message to send is {action:actionKeyword, room_id = roomId, content = message}
        // format of succesful response is {{
        //                    'type': 'message_success',
        //                    'room_id': room.id
        //                }
    // }

    }

    override suspend fun getAllRooms(): List<RoomResponse> {

        TODO("Not yet implemented")
    }

    override suspend fun getMessages(roomId: Long): List<MessageResponse> {
        val actionKeyword = "fetch_messages"
        //format of message to send is {"action":actionKeyword, "room_id" = roomId}
        TODO("Not implemented")
    }
}