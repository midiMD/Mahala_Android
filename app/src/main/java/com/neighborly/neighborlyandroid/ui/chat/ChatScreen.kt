package com.neighborly.neighborlyandroid.ui.chat

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neighborly.neighborlyandroid.data.mock.listOfConversations


import com.neighborly.neighborlyandroid.data.mock.listOfMessages
import com.neighborly.neighborlyandroid.ui.chat.components.ChatAppBar
import com.neighborly.neighborlyandroid.ui.chat.components.MessageBox
import com.neighborly.neighborlyandroid.ui.chat.components.MessageInputField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(onNavigateToChatHome:()->Unit
    ,viewModel: ChatViewModel){

    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
        topBar = {

            if (viewModel.selectedChat != null) {
                ChatAppBar(navigateToMain = {onNavigateToChatHome()}, name = viewModel.selectedChat!!.sender)
            }else{
                ChatAppBar(navigateToMain = {onNavigateToChatHome()}, name = "")
            }

        },
        containerColor = Color.Transparent,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                reverseLayout = true,
            ) {
                items(listOfMessages) { message ->
                    MessageBox(message = message,image = painterResource(id = viewModel.selectedChat!!.image) )
                }
            }
            MessageInputField()
        }
    }
}
//@Preview
//@Composable
//fun PreviewChatScreen(){
//    val viewModel:ChatViewModel = viewModel(factory = ChatViewModel.Factory)
//    viewModel.selectedChat = listOfConversations[0]
//    ChatScreen(viewModel = viewModel, onNavigateToChatHome = {})
//}