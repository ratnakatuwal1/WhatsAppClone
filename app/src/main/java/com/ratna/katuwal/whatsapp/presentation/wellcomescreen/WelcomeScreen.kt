package com.ratna.katuwal.whatsapp.presentation.wellcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ratna.katuwal.whatsapp.R

@Composable
@Preview(showSystemUi = true)
fun WelcomeScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize().statusBarsPadding()
            .background(colorResource(R.color.white))
    ) {
        Image(
            painter = painterResource(R.drawable.whatsapp_sticker), contentDescription = null,
            modifier = Modifier
                .size(300.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Welcome WhatsApp",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.padding(start = 18.dp, end = 18.dp)) {
            Text(
                text = buildAnnotatedString {
                    append("Read our ")
                    pushStyle(
                        SpanStyle(color = colorResource(R.color.light_green))
                    )
                    append("Privacy Policy")
                    pop()
                    append(". Tap \"Agree and Continue\" to accept the ")
                    pushStyle(
                        SpanStyle(color = colorResource(R.color.light_green))
                    )
                    append("Terms of Service")
                    pop()
                },
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                fontSize = 14.sp
            )

        }

        Spacer(modifier = Modifier.height(80.dp))
        Button(
            onClick = {}, modifier = Modifier.size(height = 50.dp, width = 300.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.dark_green))
        ) {
            Text(
                "Agree & Continue",
                color = colorResource(R.color.white),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }

}