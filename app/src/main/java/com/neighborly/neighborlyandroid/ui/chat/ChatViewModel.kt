package com.neighborly.neighborlyandroid.ui.chat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.neighborly.neighborlyandroid.BaseApplication
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.domain.model.Conversation
import com.neighborly.neighborlyandroid.domain.repository.ChatRepository
import com.neighborly.neighborlyandroid.ui.login.LoginScreenState

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ChatHomeScreenState{
    data object Idle : ChatHomeScreenState()
    data object Success: ChatHomeScreenState()
    data object Loading: ChatHomeScreenState()
    //Error
    data class Error(val message:String): ChatHomeScreenState()

}

class ChatViewModel(private val chatRepository: ChatRepository,
                    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _uiState = MutableStateFlow<ChatHomeScreenState>(ChatHomeScreenState.Idle) // what we modify in the view model
    val uiState = _uiState.asStateFlow() // expose it as read-only

    private val _listOfConversations = MutableStateFlow<List<Conversation>>(emptyList()) // what we modify in the view model
    val listOfConversations = _listOfConversations.asStateFlow() // expose it as read-only
    val newMessageCount:Int by mutableIntStateOf(2)
    //private set

    init {
        Log.d("logs", "ChatViewModel instantiated")
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val chatRepository =
                    (this[APPLICATION_KEY]  as BaseApplication).appCompositionRoot.chatRepository
                ChatViewModel(
                    chatRepository = chatRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
    fun getListOfConversations(){
        _uiState.value = ChatHomeScreenState.Loading
        viewModelScope.launch{
            val apiResponse = chatRepository.getListOfRooms()
            when(apiResponse){
                is Resource.Error.ClientError,
                is Resource.Error.NetworkError -> {
                    _uiState.value = ChatHomeScreenState.Error(message = "Check your intenrnet connection")
                }
                is Resource.Success -> {
                    LoginScreenState.Idle
                }

                else -> {
                    _uiState.value = ChatHomeScreenState.Idle
                }
            }
        }
    }
}
