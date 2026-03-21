package com.ratna.katuwal.whatsapp.model

data class Message(
    val senderPhoneNumber: String,
    val messageText: String,
    val timestamp: Long
)
