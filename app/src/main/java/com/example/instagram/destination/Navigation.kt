package com.example.instagram.destination

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InstagramNavigation(themeSetting: ThemeSetting) {

    val navController = rememberNavController()
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val routesWithoutTopBar = listOf(
        Screens.LogInScreen.route,
        Screens.SignUpScreen.route
    )

    Scaffold(
        topBar = {
            if (currentRoute !in routesWithoutTopBar) {
                TopAppBar(
                    title = { Text("Instagram Clone") },
                    actions = {
                        IconButton(onClick = { /* Do something */ }) {
                            Icon(Icons.Filled.Settings, contentDescription = "Settings")
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = if (currentRoute !in routesWithoutTopBar) innerPadding.calculateTopPadding() else 0.dp
                )
        ) {
            NavHost(navController = navController, startDestination = Screens.LogInScreen.route) {

                composable(Screens.SplashScreen.route) {
                    SplashScreen(
                        navController = navController,
                        authenticationViewModel = authenticationViewModel
                    )
                }

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

                composable(Screens.FeedsScreen.route) {
                    FeedsScreen(navController = navController)
                }

                composable(Screens.ProfileScreen.route) {
                    ProfileScreen(navController = navController)
                }

                composable(Screens.SearchScreen.route) {
                    SearchScreen(navController = navController)
                }
            }
        }
    }
}