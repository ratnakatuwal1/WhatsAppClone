package com.ratna.katuwal.whatsapp.presentation.splashscreen

import android.app.Activity
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.auth.FirebaseAuth
import com.ratna.katuwal.whatsapp.R
import com.ratna.katuwal.whatsapp.presentation.navigations.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SplashScreen(
    navHostController: NavHostController
) {
    val activity = LocalContext.current as Activity
    val appUpdateManager = remember { AppUpdateManagerFactory.create(activity) }
    LaunchedEffect(Unit) {
        try {
            val info = appUpdateManager.appUpdateInfo.await() // safer way

            if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    AppUpdateType.FLEXIBLE,
                    activity,
                    1001
                )
            } else {
                delay(1500)
                navigateNext(navHostController)
            }

        } catch (e: Exception) {
            // 🚨 VERY IMPORTANT fallback
            delay(1500)
            navigateNext(navHostController)
        }
    }

        Box(modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()) {
            Image(
                painter = painterResource(R.drawable.whatsapp_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
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

    private fun navigateNext(navHostController: NavHostController) {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            // ✅ User already logged in → go to Home
            navHostController.navigate(Routes.HomeScreen) {
                popUpTo(Routes.SplashScreen) {
                    inclusive = true
                }
            }
        } else {
            // ❌ Not logged in → go to Welcome
            navHostController.navigate(Routes.WelcomeScreen) {
                popUpTo(Routes.SplashScreen) {
                    inclusive = true
                }
            }
        }
    }