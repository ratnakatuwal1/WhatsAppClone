package com.ratna.katuwal.whatsapp.presentation.communityscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ratna.katuwal.whatsapp.R
import com.ratna.katuwal.whatsapp.presentation.bottomnavigation.BottomNavigation
import com.ratna.katuwal.whatsapp.presentation.updatescreen.TopBar

@Composable
@Preview(showSystemUi = true)
fun CommunityScreen(modifier: Modifier = Modifier) {
    var isSearching by remember {
        mutableStateOf(false)
    }

    var search by remember {
        mutableStateOf("")
    }

    var showMenu by remember {
        mutableStateOf(false)
    }

    var sampleCommunities = listOf(
        Communities(image = R.drawable.img, name = "Tech Enthusiasts", memberCount = "100 members"),
        Communities(image = R.drawable.img, name = "Photography Lover", memberCount = "100 members"),
        Communities(image = R.drawable.img, name = "Traveller United", memberCount = "100 members"),
        Communities(image = R.drawable.img, name = "Android Developers", memberCount = "100 members")
    )
    Scaffold(topBar = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        ) {
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isSearching) {
                        TextField(
                            value = search,
                            onValueChange = {
                                search = it
                            },
                            placeholder = {
                                Text(
                                    "Search"
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp),
                            singleLine = true
                        )
                    } else {
                        Text(
                            text = "Communities",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.black),
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)

                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    if (isSearching) {
                        IconButton(onClick = {
                            isSearching = false
                            search = ""
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.cross),
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
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

                        IconButton(onClick = {
                            showMenu = true
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.more),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )

                            DropdownMenu(
                                expanded = showMenu, onDismissRequest = {
                                    showMenu = false
                                }) {

                                DropdownMenuItem(text = {
                                    Text(
                                        "Settings"
                                    )
                                }, onClick = {
                                    showMenu = false
                                })
                            }
                        }
                    }
                }
                HorizontalDivider()
            }
        }
    }, bottomBar = {
        BottomNavigation()
    }) {
        Column(modifier = Modifier.padding(it)) {
            Button(
                onClick = {

                }, colors = ButtonDefaults.buttonColors(colorResource(R.color.light_green)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    "Start a new community",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colorResource(R.color.white)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Your Communities",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.black),
                modifier = Modifier.padding(16.dp, 8.dp)
            )

            LazyColumn() {
                items(sampleCommunities) {
                    CommunityItemDesign(communities = it)
                }
            }
        }
    }
}