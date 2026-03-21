package com.ratna.katuwal.whatsapp.presentation.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.remember
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
import coil3.compose.rememberAsyncImagePainter
import com.ratna.katuwal.whatsapp.R
import com.ratna.katuwal.whatsapp.presentation.viewmodel.BaseViewModel

@Composable
fun ChatDesign(
    modifier: Modifier = Modifier,
    chatListModel: ChatListModel,
    onClick: () -> Unit,
    baseViewModel: BaseViewModel
) {
    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val profileImage = chatListModel?.profileImage
        val bitmap = remember() {
            profileImage?.let { baseViewModel.base64ToBitmap(it) }
        }
        Image(
            painter = if (bitmap != null) {
                rememberAsyncImagePainter(bitmap)
            } else {
                painterResource(R.drawable.img_1)
            },
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .background(colorResource(R.color.teal_700))
                .clip(CircleShape), contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    chatListModel.name?: "Unknown",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    chatListModel.time?: "--:--",
                    fontSize = 14.sp,
                    color = colorResource(R.color.teal_700)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                chatListModel.message?: "message",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.teal_700)
            )
        }
    }
}