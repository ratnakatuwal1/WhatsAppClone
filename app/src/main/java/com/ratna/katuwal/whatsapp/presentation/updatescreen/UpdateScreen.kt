package com.ratna.katuwal.whatsapp.presentation.updatescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ratna.katuwal.whatsapp.R
import com.ratna.katuwal.whatsapp.presentation.bottomnavigation.BottomNavigation

@Composable
@Preview(showSystemUi = true)
fun UpdateScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val statusData = listOf(
        sampleStatusData(image = R.drawable.disha_patani, name = "Disha Patani", time = "10:00 AM"),
        sampleStatusData(image = R.drawable.sai_pallavi, name = "Sai Pallavi", time = "10:00 AM"),
        sampleStatusData(image = R.drawable.carryminati, name = "Carry Minati", time = "10:00 AM")
    )

    val sampleChannel = listOf(
        Channels(
            image = R.drawable.neat_roots,
            name = "Neat Roots",
            description = "Latest tech news"
        ),
        Channels(
            image = R.drawable.img,
            name = "Food Lover",
            description = "Discovers new recipes"
        ),
        Channels(image = R.drawable.meta, name = "Meta", description = "Explore the worlds")
    )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
                contentColor = colorResource(R.color.white),
                containerColor = colorResource(R.color.light_green),
                modifier = Modifier.size(65.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_photo_camera_24),
                    contentDescription = null
                )
            }
        }, bottomBar = {
            BottomNavigation()
        }, topBar = {
            TopBar()
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Text(
                "Status",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.black),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            MyStatus()
            statusData.forEach {
                StatusItem(statusData = it)
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                color = colorResource(R.color.teal_700)
            )

            Text(
                "Channels",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.black),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            Text(
                "Stay updated on the topic that matter to you. Find channels to follow.",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.teal_700),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Find Channels follow.",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.teal_700),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            sampleChannel.forEach {
                ChannelItem(channels = it)

            }
        }
    }
}