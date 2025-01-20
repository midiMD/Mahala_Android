package com.neighborly.neighborlyandroid.ui.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import com.neighborly.neighborlyandroid.BaseApplication
import com.neighborly.neighborlyandroid.R
import com.neighborly.neighborlyandroid.data.mock.listOfMessages
import com.neighborly.neighborlyandroid.domain.model.Message
import com.neighborly.neighborlyandroid.domain.repository.ChatRepository
import com.neighborly.neighborlyandroid.ui.navigation.ConvoRoute

class ConvoViewModel(private val chatRepository: ChatRepository,
                     private val savedStateHandle: SavedStateHandle
) : ViewModel()  {
    val convoId = savedStateHandle.toRoute<ConvoRoute>().convoId
    var senderProfilePicture: Int
        get() = savedStateHandle["senderProfileImage"] ?: R.drawable.ic_broken_img
        set(value) {
            savedStateHandle["senderProfileImage"] = value
        }
    var counter: Int
        get() = savedStateHandle["counter"] ?: 0
        set(value) {
            savedStateHandle["counter"] = value
        }

    fun incrementCounter() {
        counter++
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
    fun getMessages():List<Message>{
        return listOfMessages
    }

}