package com.example.chatbot.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.chatbot.ChatData
import com.example.chatbot.ChatRoleEnum
import com.example.chatbot.ui.theme.fontFamily
import java.text.DateFormat
import java.util.Date

@Composable
fun ChatScreen() {
    var chatHistory by remember { mutableStateOf(mutableListOf<ChatData>()) }

    // Fungsi untuk menambah pesan baru ke chatHistory
    fun sendMessage(text: String) {
        // Menambahkan pesan pengguna ke chatHistory
        chatHistory.add(ChatData(message = text, role = "user"))

        // Simulasi balasan dari bot (misalnya dari API atau Gemini)
        val botReply = "Bot response to: $text"  // Gantilah dengan respon dari Gemini
        chatHistory.add(ChatData(message = botReply, role = "bot"))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Menampilkan chat history
        ChatList(list = chatHistory)

        // Footer untuk mengirimkan pesan
        ChatFooter(onClick = { sendMessage(it) })
    }
}

@Composable
fun ChatList(
    list: MutableList<ChatData>
) {
    val scrollState = rememberLazyListState()

    LaunchedEffect(list.size) {
        if (list.isNotEmpty()) {
            scrollState.animateScrollToItem(list.size - 1)
        }
    }

    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(list) { message ->
            val formattedMessage = parseMarkdownToBold(message.message)

            if (message.role == ChatRoleEnum.USER.role) {
                // Pesan dari pengguna di sisi kanan
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFCE5B8), shape = RoundedCornerShape(12.dp))
                            .padding(12.dp)
                            .widthIn(max = 300.dp)
                    ) {
                        // Jika pesan adalah URL gambar, tampilkan gambar
                        if (isImageUrl(message.message)) {
                            AsyncImage(
                                model = message.message,
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.Crop,
//                                error = { Text("Gambar gagal dimuat") },
//                                placeholder = { Text("Memuat gambar...") }
                            )
                        } else {
                            Text(
                                text = formattedMessage,
                                color = Color(0xFFFF914D),
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            } else {
                // Pesan dari bot di sisi kiri
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFF914D), shape = RoundedCornerShape(12.dp))
                            .padding(12.dp)
                            .widthIn(max = 300.dp)
                    ) {
                        // Jika pesan adalah URL gambar, tampilkan gambar
                        if (isImageUrl(message.message)) {
                            AsyncImage(
                                model = message.message,
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.Crop,
//                                error = { Text("Gambar gagal dimuat") },
//                                placeholder = { Text("Memuat gambar...") }
                            )
                        } else {
                            Text(
                                text = formattedMessage,
                                color = Color(0xFFFCE5B8),
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// Fungsi untuk mengecek apakah pesan adalah URL gambar
fun isImageUrl(message: String): Boolean {
    return message.startsWith("http") && (
            message.endsWith(".jpg") ||
                    message.endsWith(".jpeg") ||
                    message.endsWith(".png") ||
                    message.endsWith(".gif")
            )
}

@Composable
fun parseMarkdownToBold(message: String): AnnotatedString {
    return buildAnnotatedString {
        var currentIndex = 0
        while (currentIndex < message.length) {
            when {
                // Deteksi format **bold**
                message.startsWith("**", currentIndex) -> {
                    val start = currentIndex + 2
                    val end = message.indexOf("**", start)
                    if (end != -1) {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(message.substring(start, end))
                        }
                        currentIndex = end + 2
                    } else {
                        append(message.substring(currentIndex))
                        break
                    }
                }
                // Deteksi format *italic*
                message.startsWith("*", currentIndex) -> {
                    val start = currentIndex + 1
                    val end = message.indexOf("*", start)
                    if (end != -1) {
                        withStyle(style = SpanStyle(fontStyle = FontStyle.Normal)) {
                            append(message.substring(start, end))
                        }
                        currentIndex = end + 1
                    } else {
                        append(message.substring(currentIndex))
                        break
                    }
                }

                message.startsWith("*", currentIndex) -> {
                    val start = currentIndex + 1
                    val end = message.indexOf("*", start)
                    if (end != -1) {
                        withStyle(style = SpanStyle(fontStyle = FontStyle.Normal)) {
                            append(message.substring(start, end))
                        }
                        currentIndex = end + 1
                    } else {
                        append(message.substring(currentIndex))
                        break
                    }
                }


                else -> {
                    append(message[currentIndex])
                    currentIndex++
                }
            }
        }
    }
}