package com.example.instagram.presentation.screen.Profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.instagram.destination.BottomNavigationItem
import com.example.instagram.destination.BottomNavigationMenu
import com.example.instagram.presentation.Authentication.UserViewModel
import com.example.instagram.presentation.screen.Profile.Components.RoundedImage
import com.example.instagram.utils.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {

    val userViewModel: UserViewModel = hiltViewModel()
    userViewModel.getUserInfo()

    when (val response = userViewModel.getUserDate.value) {
        is Response.Loading -> {
            CircularProgressIndicator()
        }

        is Response.Success -> {
            if (response.data != null) {
                val obj = response.data
                var selectedTabIndex by remember { mutableStateOf(0) }

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = obj.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic
                                )
                            },
                            actions = {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add",
                                    tint = Color.Black,
                                    modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    imageVector = Icons.Filled.List,
                                    contentDescription = "HamBurgerBar",
                                    tint = Color.Black,
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(
                                    start = 10.dp, top = 10.dp, bottom = 10.dp, end = 20.dp
                                )
                            ) {
                                RoundedImage(
                                    image = rememberImagePainter(data = obj.imageUrl),
                                    modifier = Modifier
                                        .size(100.dp)
                                        .weight(3.5f)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Row (
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(6.5f)
                                ){

                                }
                            }
                        }
                    }
                }
            }
        }

        is Response.Error -> {

        }
    }

    userViewModel.setUserInfo(
        "Krishna",
        "krishna8816",
        "krisha123.com",
        "web and android developer"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Profile")
        }
        BottomNavigationMenu(
            seletedItem = BottomNavigationItem.PROFILE,
            navController = navController
        )
    }
}