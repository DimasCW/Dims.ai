package com.example.chatbot

data class ChatData (
    val message : String,
    val role : String,
    val timestamp: Long = System.currentTimeMillis() // Waktu pesan
)

enum class ChatRoleEnum(val role : String){
    USER("user"),
    MODEL("model")

}