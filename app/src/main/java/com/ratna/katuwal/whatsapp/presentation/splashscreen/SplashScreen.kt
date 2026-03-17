package com.ratna.katuwal.whatsapp.presentation.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.ratna.katuwal.whatsapp.R
import com.ratna.katuwal.whatsapp.presentation.navigations.Routes
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay

@Composable
@Preview(showSystemUi = true)
fun SplashScreen(navHostController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(1500)
        navHostController.navigate(Routes.WelcomeScreen){
            popUpTo(Routes.SplashScreen) {
                inclusive = true
            }
        }

    }
    Box(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
        Image(
            painter = painterResource(R.drawable.whatsapp_icon),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "from",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.meta),
                    contentDescription = null,
                    tint = colorResource(R.color.light_green),
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    "Meta",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.light_green),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }

}