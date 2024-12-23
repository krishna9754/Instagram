package com.example.instagram.destination

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.instagram.mode.AppTheme
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
fun InstagramNavigation(themeSetting: ThemeSetting, onItemSelection: (AppTheme) -> Unit,) {

    val navController = rememberNavController()
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
    val menuExpand = remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val routesWithoutTopBar = listOf(
        Screens.LogInScreen.route,
        Screens.SignUpScreen.route,
        Screens.SplashScreen.route,
    )

    Scaffold(
        topBar = {
            if (currentRoute !in routesWithoutTopBar) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(Color.White),
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Compose Theme")
                        }
                    },
                    actions = {
                        IconButton(onClick = { menuExpand.value = true }) {
                            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                        }

                        DropdownMenu(
                            expanded = menuExpand.value,
                            onDismissRequest = { menuExpand.value = false },
                            modifier = Modifier.width(250.dp)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Auto") },
                                onClick = {
                                    onItemSelection(AppTheme.MODE_AUTO)
                                    menuExpand.value = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Light Mode") },
                                onClick = {
                                    onItemSelection(AppTheme.MODE_DAY)
                                    menuExpand.value = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Dark") },
                                onClick = {
                                    onItemSelection(AppTheme.MODE_NIGHT)
                                    menuExpand.value = false
                                }
                            )
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
                .background(color = Color.White)
        ) {
            NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {

                composable(Screens.SplashScreen.route) {
                    SplashScreen(
                        navController = navController,
                        authenticationViewModel = authenticationViewModel
                    )
                }

                composable(Screens.LogInScreen.route) {
                    LogInScreen(
                        navController = navController,
                        authenticationViewModel = authenticationViewModel
                    )
                }

                composable(Screens.SignUpScreen.route) {
                    SignUpScreen(
                        navController = navController,
                        authenticationViewModel = authenticationViewModel
                    )
                }

                composable(Screens.ProfileScreen.route) {
                    ProfileScreen(navController = navController)
                }

                composable(Screens.FeedsScreen.route) {
                    FeedsScreen(navController = navController)
                }

                composable(Screens.SearchScreen.route) {
                    SearchScreen(navController = navController)
                }
            }
        }
    }
}