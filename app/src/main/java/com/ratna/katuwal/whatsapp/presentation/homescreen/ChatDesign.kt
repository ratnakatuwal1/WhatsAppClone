package com.ratna.katuwal.whatsapp.presentation.homescreen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ratna.katuwal.whatsapp.R

@Composable
fun ChatDesign(modifier: Modifier = Modifier,
               chatListModel: ChatListModel) {
    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(chatListModel.image),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape), contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    chatListModel.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    chatListModel.time,
                    color = colorResource(R.color.teal_700)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                chatListModel.message,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.teal_700)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatDesignPreview() {

    val sampleChat = ChatListModel(
        image = R.drawable.salman_khan,
        name = "Salman Khan",
        message = "Hello",
        time = "10:00 AM"
    )

    ChatDesign(chatListModel = sampleChat)
}