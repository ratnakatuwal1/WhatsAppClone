package com.ratna.katuwal.whatsapp.presentation.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ratna.katuwal.whatsapp.presentation.callscreen.CallScreen
import com.ratna.katuwal.whatsapp.presentation.communityscreen.CommunityScreen
import com.ratna.katuwal.whatsapp.presentation.homescreen.HomeScreen
import com.ratna.katuwal.whatsapp.presentation.splashscreen.SplashScreen
import com.ratna.katuwal.whatsapp.presentation.updatescreen.UpdateScreen
import com.ratna.katuwal.whatsapp.presentation.userregistration.UserRegistration
import com.ratna.katuwal.whatsapp.presentation.wellcomescreen.WelcomeScreen

@Composable
@Preview(showSystemUi = true)
fun WhatsAppNavigationSystem(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(startDestination = Routes.SplashScreen, navController = navController){
        composable<Routes.SplashScreen> {
            SplashScreen(navController)
        }

        composable<Routes.WelcomeScreen> {
            WelcomeScreen(navController)
        }

        composable<Routes.UserRegistrationScreen> {
            UserRegistration()
        }

        composable<Routes.HomeScreen> {
            HomeScreen()
        }

        composable<Routes.UpdateScreen> {
            UpdateScreen()
        }

        composable<Routes.CommunitiesScreen> {
            CommunityScreen()
        }

        composable<Routes.CallsScreen> {
            CallScreen()
        }
    }
    
}