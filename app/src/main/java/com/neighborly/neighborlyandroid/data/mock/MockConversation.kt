package com.neighborly.neighborlyandroid.data.mock

import com.neighborly.neighborlyandroid.R
import com.neighborly.neighborlyandroid.domain.model.Conversation


val listOfConversations = listOf(
    Conversation(
        id=1,
        sender = "Janet Fowler",
        image = R.drawable.janet,
        amILastSender = false,
        message = "I'm going to San Francisco for a few days. I'll be back on Monday.",
        time = "now",
        unread = true,
    ),
    Conversation(
        id = 2,
        sender = "Jason Boyd",
        image = R.drawable.jason,
        amILastSender = true,
        message = "Sounds good!",
        time = "16:23",
        unread = false,
    ),
    Conversation(
        id= 3,
        sender = "Nicolas Dunn",
        image = R.drawable.nicolas,
        amILastSender = false,
        message = "See you there.",
        time = "16:22",
        unread = true,
    )
)