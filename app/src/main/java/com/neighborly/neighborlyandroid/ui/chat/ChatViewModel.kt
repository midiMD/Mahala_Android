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
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.neighborly.neighborlyandroid.BaseApplication
import com.neighborly.neighborlyandroid.domain.model.Conversation
import com.neighborly.neighborlyandroid.domain.repository.ChatRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class ChatViewModel(private val chatRepository: ChatRepository,
                    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var selectedChat: Conversation? by mutableStateOf(null)
    var newMessageCount:Int by mutableIntStateOf(2)
    private set

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

    fun selectChat(value:Conversation?){
        selectedChat = value
    }

}
