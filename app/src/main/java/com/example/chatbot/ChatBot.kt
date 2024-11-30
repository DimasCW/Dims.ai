package com.example.chatbot

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatbot.Component.ChatFooter
import com.example.chatbot.Component.ChatHeader
import com.example.chatbot.Component.ChatList


@Composable
fun ChatBot(modifier: Modifier = Modifier,
            viewModel: ChatBotVM = viewModel()
){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(Color.White)
    ){

        ChatHeader()

        Box(modifier = Modifier
            .weight(1f),
            contentAlignment = Alignment.Center

        ){
            if(viewModel.list.isEmpty()){
                Text(
                    text = "Kami akan menjawab semua pertanyaan mu",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 25.sp,
                    color = Color(0xFFFF914D)
                )
            }
            ChatList(list = viewModel.list)
        }

        ChatFooter {
            if(it.isNotEmpty()){

                viewModel.sendMessage(it)
            }
        }
    }
}