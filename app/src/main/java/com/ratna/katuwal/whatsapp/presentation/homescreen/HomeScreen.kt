package com.ratna.katuwal.whatsapp.presentation.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.ratna.katuwal.whatsapp.R
import com.ratna.katuwal.whatsapp.presentation.bottomnavigation.BottomNavigation
import com.ratna.katuwal.whatsapp.presentation.navigations.Routes
import com.ratna.katuwal.whatsapp.presentation.viewmodel.BaseViewModel

@Composable
fun HomeScreen(
   navHostController: NavHostController,
    homeBaseViewModel: BaseViewModel
) {
    var showPopup by remember {
        mutableStateOf(false)
    }
    val chatData by homeBaseViewModel.chatList.collectAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    if (userId != null) {
        LaunchedEffect(Unit) {
            homeBaseViewModel.getChatForUser(userId) { chats ->


            }

        }
    }

    var showMenu by remember {
        mutableStateOf(false)
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                showPopup = true
            },
            containerColor = colorResource(R.color.light_green),
            modifier = Modifier.size(65.dp),
            contentColor = colorResource(R.color.white)
        ) {
            Icon(
                painter = painterResource(R.drawable.chat_icon),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = colorResource(R.color.white)
            )
        }
    }, bottomBar = {
        BottomNavigation(navHostController = navHostController, selectedItem = 0, onClick = {index ->
            when(index){
                0 -> {
                    navHostController.navigate(Routes.HomeScreen)
                }

                1 -> {
                    navHostController.navigate(Routes.UpdateScreen)
                }

                2 -> {
                    navHostController.navigate(Routes.CommunitiesScreen)
                }

                3 -> {
                    navHostController.navigate(Routes.CallsScreen)
                }
            }
        })
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(color = colorResource(R.color.white))
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                var isSearching by remember {
                    mutableStateOf(false)
                }

                var searchText by remember {
                    mutableStateOf("")
                }

                var showMenu by remember {
                    mutableStateOf(false)
                }

                if (isSearching) {
                    TextField(
                        value = searchText, onValueChange = {
                            searchText = it
                        }, placeholder = {
                            Text(
                                "Search",
                                color = colorResource(R.color.teal_700),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }, singleLine = true,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 12.dp)
                            .fillMaxWidth(0.8f),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        )
                    )
                } else {
                    Text(
                        "WhatsApp",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.light_green),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 12.dp)
                    )

                    Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                        IconButton(onClick = {
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.camera),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        if (isSearching) {
                            IconButton(onClick = {
                                isSearching = false
                                searchText = ""
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.cross),
                                    contentDescription = null,
                                    tint = colorResource(R.color.teal_700),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else {
                            IconButton(onClick = {
                                isSearching = true

                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.search),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        IconButton(onClick = {
                            showMenu = !showMenu
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.more),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )

                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = {
                                    showMenu = false
                                },
                                modifier = Modifier.background(color = colorResource(R.color.white))
                            ) {
                                DropdownMenuItem(text = {
                                    Text("New Group")
                                }, onClick = {
                                    showMenu = false
                                })

                                DropdownMenuItem(text = {
                                    Text("New Broadcast")
                                }, onClick = {
                                    showMenu = false
                                })

                                DropdownMenuItem(text = {
                                    Text("Linked Device")
                                }, onClick = {
                                    showMenu = false
                                })

                                DropdownMenuItem(text = {
                                    Text("Starred Message")
                                }, onClick = {
                                    showMenu = false
                                })

                                DropdownMenuItem(text = {
                                    Text("Setting")
                                }, onClick = {
                                    showMenu = false
                                    navHostController.navigate(Routes.SettingScreen)
                                })
                            }
                        }
                    }
                }


            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            if (showPopup) {
                AddUserPopup(onDissmiss = {
                    showPopup = false
                }, onUserAdd = { newUser ->
                    homeBaseViewModel.addChat(newUser)
                }, baseViewModel = homeBaseViewModel)
            }

            LazyColumn() {
                items(chatData) { chat ->
                    ChatDesign(chatListModel = chat, onClick = {
                        navHostController.navigate(
                            Routes.ChatScreen.createRoutes(
                                phoneNumber = chat.phoneNumber ?: "ok"
                            )
                        )
                    }, baseViewModel = homeBaseViewModel)
                }
            }

        }
    }
}


@Composable
fun AddUserPopup(
    modifier: Modifier = Modifier,
    onDissmiss: () -> Unit,
    onUserAdd: (ChatListModel) -> Unit,
    baseViewModel: BaseViewModel
) {
    var phoneNumber by remember {
        mutableStateOf("")
    }

    var isSearching by remember {
        mutableStateOf(false)
    }

    var userFound by remember {
        mutableStateOf<ChatListModel?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
            }, label = {
                Text("Enter Your Phone Number")
            }, modifier = Modifier.fillMaxWidth(), singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        Row() {
            Button(
                onClick = {
                    isSearching = true
                    baseViewModel.searchUserByPhoneNumber(phoneNumber) { user ->
                        isSearching = false
                        if (user != null) {
                            userFound = user
                        } else {
                            userFound = null
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.light_green))
            ) {
                Text(
                    "Search",
                    color = colorResource(R.color.white),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onDissmiss,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.light_green))
            ) {
                Text(
                    "Cancel",
                    color = colorResource(R.color.white),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        if (isSearching) {
            Text(
                "Searching...",
                color = colorResource(R.color.teal_700)
            )
        }

        userFound?.let {
            Column() {
                Text("User Found ${it.name}")

                Button(
                    onClick = {
                        onUserAdd(it)
                        onDissmiss()
                    }, colors = ButtonDefaults.buttonColors(colorResource(R.color.light_green))
                ) {
                    Text("Add to Chat")
                }
            }
        } ?: run {
            if (!isSearching) {
                Text(
                    "No User found with this number",
                    color = colorResource(R.color.teal_700)
                )
            }
        }
    }

}