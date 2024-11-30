@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.chatbot.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.chatbot.ui.theme.fontFamily

@Composable
fun ChatFooter(
    onClick: (text: String) -> Unit
) {
    var inputText: String by remember {
        mutableStateOf("")
    }

    // Mendapatkan kontrol keyboard
    val keyboardController = LocalSoftwareKeyboardController.current

    // Gunakan Modifier untuk menyertakan padding saat keyboard muncul
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .imePadding() // Agar tidak tertutup keyboard
    ) {
        // Row untuk Input TextField dan Icon Button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White) // Ganti dengan putih untuk latar belakang lebih cerah
                .padding(1.dp)
                .navigationBarsPadding() // Memberikan padding agar tidak tertutup bar navigasi
        ) {
            // Input TextField
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text(text = "Malu bertanya sesat dijalan", fontFamily = fontFamily) },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp) // Menambahkan padding pada sisi kanan untuk jarak ke icon
                    .background(Color(0xFFEFEFEF)), // Background lebih terang untuk input
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFFCE5B8) // Mengatur warna background textfield
                )
            )

            // Icon Button untuk mengirimkan pesan
            IconButton(
                onClick = {
                    if (inputText.isNotEmpty()) {
                        onClick(inputText)
                        inputText = ""
                        // Menutup keyboard setelah mengirimkan pesan
                        keyboardController?.hide()
                    }
                },
                modifier = Modifier
                    .size(48.dp) // Ukuran icon sedikit lebih besar agar lebih jelas
                    .clip(CircleShape)
                    .background(Color(0xFFFF914D)) // Ganti dengan warna hijau gelap
                    .padding(12.dp) // Memberikan padding di sekitar icon
            ) {
                Icon(
                    imageVector = Icons.Rounded.Send,
                    contentDescription = "Send Message",
                    tint = Color.White
                )
            }
        }
    }
}
