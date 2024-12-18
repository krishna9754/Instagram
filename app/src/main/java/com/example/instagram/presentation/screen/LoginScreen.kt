package com.example.instagram.presentation.screen

import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.instagram.ComposeThemeScreen
import com.example.instagram.R
import com.example.instagram.mode.ThemeSetting
import com.example.instagram.presentation.Authentication.AuthenticationViewModel
import com.example.instagram.presentation.Toast
import com.example.instagram.utils.Response
import com.example.instagram.utils.Screens


@Composable
fun LogInScreen(
    navController: NavHostController,
    themeSetting: ThemeSetting,
    authenticationViewModel: AuthenticationViewModel,
) = ComposeThemeScreen(
    onItemSelection = { theme -> themeSetting.theme = theme },
    navController = navController
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(it)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.instagram),
            contentDescription = "LogIn Screen"
        )
        Text(
            text = "Sign In",
            modifier = Modifier.padding(10.dp),
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = { emailState -> email.value = emailState },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            label = { Text(text = "Enter Your Email") }
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { passwordState -> password.value = passwordState },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            label = { Text(text = "Enter Your Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                authenticationViewModel.signIn(
                    email = email.value,
                    password = password.value
                )
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Sign In")
            when (val response = authenticationViewModel.signInState.value) {
                is Response.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                }

                is Response.Success -> {
                    if (response.data) {
                        navController.navigate(Screens.FeedsScreen.route) {
                            popUpTo(Screens.LogInScreen.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        Toast(message = "SignIn Failed")
                    }
                }

                is Response.Error -> {
                    Toast(message = response.message)
                }
            }
            Text(
                text = "New User? Sign Up",
                color = androidx.compose.ui.graphics.Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(Screens.SignUpScreen.route
                        ) {
                            launchSingleTop = true
                        }
                    }
            )
        }
    }
}