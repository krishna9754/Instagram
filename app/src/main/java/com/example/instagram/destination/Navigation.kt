package com.example.instagram.destination

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instagram.mode.ThemeSetting
import com.example.instagram.presentation.Authentication.AuthenticationViewModel
import com.example.instagram.presentation.screen.SplashScreen
import com.example.instagram.presentation.screen.FeedsScreen
import com.example.instagram.presentation.screen.LogInScreen
import com.example.instagram.presentation.screen.ProfileScreen
import com.example.instagram.presentation.screen.SearchScreen
import com.example.instagram.presentation.screen.SignUpScreen
import com.example.instagram.utils.Screens


@Composable
fun InstagramNavigation(themeSetting: ThemeSetting) {

    val navController = rememberNavController()
    val authenticationViewModel : AuthenticationViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route ){

        composable(Screens.LogInScreen.route) {
            LogInScreen(
                navController = navController,
                themeSetting = themeSetting,
                authenticationViewModel = authenticationViewModel
            )
        }

        composable(Screens.SignUpScreen.route) {
            SignUpScreen(
                navController = navController,
                themeSetting = themeSetting,
                authenticationViewModel = authenticationViewModel
            )
        }

        composable(Screens.SplashScreen.route) {
            SplashScreen(navController = navController, authenticationViewModel = authenticationViewModel)
        }

        composable(Screens.FeedsScreen.route) {
            FeedsScreen(navController = navController)
        }

        composable(Screens.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }

        composable(Screens.SearchScreen.route) {
            SearchScreen (navController = navController)
        }
    }

}