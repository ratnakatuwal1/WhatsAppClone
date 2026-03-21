package com.ratna.katuwal.whatsapp.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ratna.katuwal.whatsapp.model.Message
import com.ratna.katuwal.whatsapp.presentation.homescreen.ChatListModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okio.IOException
import java.io.ByteArrayInputStream
import java.io.InputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class BaseViewModel : ViewModel() {
    fun searchUserByPhoneNumber(phoneNumber: String, callback: (ChatListModel?) -> Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Log.e("BaseViewModel", "Current user is null")
            callback(null)
            return
        }

        val databaseReference = FirebaseDatabase.getInstance().getReference("users")
        databaseReference.orderByChild("phoneNumber").equalTo(phoneNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.children.first().getValue(ChatListModel::class.java)
                        callback(user)
                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(
                        "BaseViewModel",
                        "Error Fetching user: ${error.message}, Details: ${error.details}"
                    )
                    callback(null)
                }
            })
    }

    fun getChatForUser(userId: String, callback: (List<ChatListModel>) -> Unit) {
        val chatRef = FirebaseDatabase.getInstance().getReference("users/$userId/chats")
        chatRef.orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatList = mutableListOf<ChatListModel>()
                    for (childSnapshot in snapshot.children) {
                        val chat = childSnapshot.getValue(ChatListModel::class.java)
                        if (chat != null) {
                            chatList.add(chat)
                        }
                    }
                    callback(chatList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModer", "Error fetching chat: ${error.message}")
                    callback(emptyList())
                }

            })
    }

    private val _chatList = MutableStateFlow<List<ChatListModel>>(emptyList())
    val chatList = _chatList.asStateFlow()

    init {
        loadChatData()
    }

    private fun loadChatData() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserId != null) {
            val chatRef = FirebaseDatabase.getInstance().getReference("chats")
            chatRef.orderByChild("userId").equalTo(currentUserId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val chatList = mutableListOf<ChatListModel>()
                        for (childSnapshot in snapshot.children) {
                            val chat = childSnapshot.getValue(ChatListModel::class.java)
                            if (chat != null) {
                                chatList.add(chat)
                            }
                        }
                        _chatList.value = chatList
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("BaseViewModer", "Error fetching chat: ${error.message}")
                    }

                })
        }

    }

    fun addChat(newChat: ChatListModel) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserId != null) {
            val newChatRef = FirebaseDatabase.getInstance().getReference("chats").push()
            val chatWithUser = newChat.copy(currentUserId)
            newChatRef.setValue(chatWithUser).addOnSuccessListener {
                Log.d("BaseViewModel", "Chat added successfully")
            }.addOnFailureListener {
                Log.e("BaseViewModel", "Error adding chat: ${it.message}")
            }
        } else {
            Log.e("BaseViewModel", "Current user is null")
        }
    }

    private val dataBaseReference = FirebaseDatabase.getInstance().reference
    fun sendMessage(senderPhoneNumber: String, receiverPhoneNumber: String, messageText: String) {
        val messageId = dataBaseReference.push().key ?: return
        val message = Message(
            senderPhoneNumber = senderPhoneNumber,
            messageText = messageText,
            timestamp = System.currentTimeMillis()
        )

        dataBaseReference.child("message")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)
            .child(messageId)
            .setValue(message)

        dataBaseReference.child("message")
            .child(receiverPhoneNumber)
            .child(senderPhoneNumber)
            .child(messageId)
            .setValue(message)
    }

    fun getMessage(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onNewMessage: (Message) -> Unit
    ) {
        val messageRef = dataBaseReference.child("message")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)

        messageRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(
                snapshot: DataSnapshot,
                previousCHildName: String?
            ) {
                val message = snapshot.getValue(Message::class.java)
                if (message != null) {
                    onNewMessage(message)
                }
            }

            override fun onChildChanged(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun fetchLastMessageForChat(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onLastMessageFetched: (String, String) -> Unit
    ) {
        val chatRef = FirebaseDatabase.getInstance().reference
            .child("message")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)

        chatRef.orderByChild("timestamp").limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val lastMessage =
                            snapshot.children.firstOrNull()?.child("message")?.value as? String
                        val timestamp =
                            snapshot.children.firstOrNull()?.child("message")?.value as? String
                        onLastMessageFetched(lastMessage ?: "No message", timestamp ?: "--:--")
                    } else {
                        onLastMessageFetched("No message", "--:--")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onLastMessageFetched("No message", "--:--")
                    Log.e("BaseViewModel", "Error fetching last message: ${error.message}")

                }
            })
    }

    fun loadChatList(
        currentUserPhoneNumber: String,
        onChatListLoaded: (List<ChatListModel>) -> Unit
    ) {
        val chatList = mutableListOf<ChatListModel>()
        val chatRef = FirebaseDatabase.getInstance().reference
            .child("chats")
            .child(currentUserPhoneNumber)

        chatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    snapshot.children.forEach { child ->
                        val phoneNumber = child.key ?: return@forEach
                        val name = child.child("name").value as? String ?: "Unknown"
                        val image = child.child("image").value as? String

                        val profileImageBitmap = image?.let { decodeBase64ToBitmap(it) }

                        fetchLastMessageForChat(
                            currentUserPhoneNumber,
                            phoneNumber
                        ) { lastMessage, time ->
                            chatList.add(
                                ChatListModel(
                                    name = name,
                                    image = profileImageBitmap as Int?,
                                    message = lastMessage,
                                    time = time
                                )
                            )
                            if (chatList.size == snapshot.childrenCount.toInt()) {
                                onChatListLoaded(chatList)
                            }
                        }

                    }
                } else {
                    onChatListLoaded(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onChatListLoaded(emptyList())
            }
        })
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun decodeBase64ToBitmap(base64Image: String): Bitmap? {
        return try {
            val decodedByte = Base64.decode(base64Image, android.util.Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
        } catch (e : IOException) {
            null
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedByte = Base64.decode(base64String, android.util.Base64.DEFAULT)
            val inputStream: InputStream = ByteArrayInputStream(decodedByte)
            BitmapFactory.decodeStream(inputStream)
        } catch (e : IOException) {
            null
        }
    }
}