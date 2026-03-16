package com.ratna.katuwal.whatsapp.presentation.callscreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ratna.katuwal.whatsapp.R

@Composable
@Preview(showSystemUi = true)
fun FavouriteSection(modifier: Modifier = Modifier) {
    val sampleFavourite = listOf(
        FavouriteContact(R.drawable.salman_khan, "Salman Khan"),
        FavouriteContact(R.drawable.ajay_devgn, "Ajay Devgn"),
        FavouriteContact(R.drawable.sai_pallavi, "Sai Pallavi"),
        FavouriteContact(R.drawable.disha_patani, "Disha Patani"),
        FavouriteContact(R.drawable.hrithik_roshan, "Hrithik Roshan"),
        FavouriteContact(R.drawable.sharadha_kapoor, "Sharadha Kapoor"),
        FavouriteContact(R.drawable.sharukh_khan, "Sharukh Khan")
    )
    Column(modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)) {
        Text(
            "Favourite",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            sampleFavourite.forEach {
                FavouriteItem(favouriteContact = it)
            }
        }
    }
}

data class FavouriteContact(
    val image: Int,
    val name: String
)