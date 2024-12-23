package com.example.instagram.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.instagram.destination.BottomNavigationItem
import com.example.instagram.destination.BottomNavigationMenu

@Composable
fun ProfileScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Profile")
            Text(text = "Profile")
            Text(text = "Profile")
            Text(text = "Profile")
            Text(text = "Profile")
            Text(text = "Profile")
            Text(text = "Profile")
            Text(text = "Profile")
            Text(text = "Profile")
            Text(text = "Profile")
            Text(text = "Profile")
        }
        BottomNavigationMenu(seletedItem = BottomNavigationItem.PROFILE, navController = navController)
    }
}