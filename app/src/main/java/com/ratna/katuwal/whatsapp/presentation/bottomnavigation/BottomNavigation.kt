package com.ratna.katuwal.whatsapp.presentation.bottomnavigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ratna.katuwal.whatsapp.R

@Composable
fun BottomNavigation(modifier: Modifier = Modifier,
                     navHostController: NavHostController,
                     onClick:(index: Int) -> Unit,
                     selectedItem: Int
) {
//    BottomAppBar(tonalElevation = 12.dp, containerColor = colorResource(R.color.white)) {
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            Column(
//                modifier = Modifier.padding(horizontal = 16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.chat_icon),
//                    contentDescription = null,
//                    modifier = Modifier.size(28.dp)
//                )
//
//                Spacer(modifier = Modifier.height(2.dp))
//
//                Text(
//                    "Chats",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 14.sp
//                )
//            }
//
//            Column(
//                modifier = Modifier.padding(horizontal = 16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.update_icon),
//                    contentDescription = null,
//                    modifier = Modifier.size(28.dp)
//                )
//
//                Spacer(modifier = Modifier.height(2.dp))
//
//                Text(
//                    "Update",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 14.sp
//                )
//            }
//
//            Column(
//                modifier = Modifier.padding(horizontal = 16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.communities_icon),
//                    contentDescription = null,
//                    modifier = Modifier.size(28.dp)
//                )
//
//                Spacer(modifier = Modifier.height(2.dp))
//
//                Text(
//                    "Communites",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 14.sp
//                )
//            }
//
//            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
//                Icon(
//                    painter = painterResource(R.drawable.telephone),
//                    contentDescription = null,
//                    modifier = Modifier.size(28.dp)
//                )
//
//                Spacer(modifier = Modifier.height(2.dp))
//
//                Text(
//                    "Calls",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 14.sp
//                )
//            }
//
//        }
//    }

    val items = listOf(
        NavigationItem(name = "Chats", selectedIcon = R.drawable.chat_icon, unSelectedIcon = R.drawable.outline_chat_24),
        NavigationItem(name = "Updates", selectedIcon = R.drawable.update_icon, unSelectedIcon = R.drawable.update_icon),
        NavigationItem(name = "Communities", selectedIcon = R.drawable.communities_icon, unSelectedIcon = R.drawable.communities_icon),
        NavigationItem(name = "Calls", selectedIcon = R.drawable.telephone, unSelectedIcon = R.drawable.outline_phone_24)
    )

    NavigationBar(containerColor = colorResource(R.color.white), modifier = Modifier.height(80.dp)) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(selected = selectedItem == index, onClick = {onClick(index)},
                label = {
                    if (index == selectedItem) {
                        Text(
                            item.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = colorResource(R.color.black)
                        )
                    } else {
                        Text(
                            item.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = colorResource(R.color.teal_700)
                        )
                    }
                }, icon = {
                    Icon(
                        painter = if (index == selectedItem) {
                            painterResource(item.selectedIcon)
                        } else {
                            painterResource(item.unSelectedIcon)
                }, contentDescription = null,
                        tint = if (index == selectedItem) {
                            colorResource(R.color.teal_700)
                        } else {
                            colorResource(R.color.black)
                        }, modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                },
                colors =  NavigationBarItemDefaults.colors(indicatorColor = colorResource(R.color.mint_green))
            )
        }
    }
}

data class NavigationItem(
    val name: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int
)