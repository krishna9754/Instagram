package com.example.instagram.utils

sealed class Screens( val route: String) {
    object SplashScreen : Screens ( "splash_screen")
    object LogInScreen : Screens ("login_screen")
    object SignUpScreen : Screens ("signup_screen")
    object FeedsScreen : Screens ("feeds_screen")
}