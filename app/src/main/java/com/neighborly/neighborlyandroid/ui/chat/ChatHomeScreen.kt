package com.neighborly.neighborlyandroid.ui.chat

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neighborly.neighborlyandroid.data.mock.listOfConversations
import com.neighborly.neighborlyandroid.ui.chat.components.ConversationItem
import com.neighborly.neighborlyandroid.ui.chat.components.HomeAppBar


@Composable
fun ChatHomeScreen(onConvoClick:(convoId:Long)->Unit,
                   viewModel: ChatViewModel
) {
    Scaffold(
        topBar = {
            HomeAppBar(newMessageCount =viewModel.newMessageCount )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            items(listOfConversations) { conversation ->
                //Text("asdads")
                ConversationItem(
                    conversation = conversation,
                    onClick = {
                        viewModel.saveSenderImage(conversation.image)
                        onConvoClick(conversation.id)
                    }
                )
            }

        }

    }
}