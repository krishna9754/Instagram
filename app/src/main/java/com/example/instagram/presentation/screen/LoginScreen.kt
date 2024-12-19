package com.example.instagram.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
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
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.instagram),
            contentDescription = "LogIn Screen",
            modifier = Modifier.width(220.dp),
            colorFilter = ColorFilter.tint(Color.Black)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp),
            onClick = {  },
            colors = ButtonDefaults.buttonColors(Color(0xff4c76be))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "LogIn Screen",
                    modifier = Modifier.size(40.dp)
                )
                Text(text = "Continue with Facebook", fontSize = 17.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(10.dp))
        
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(modifier = Modifier.width(130.dp))
            Text(text = "OR")
            HorizontalDivider(modifier = Modifier.width(130.dp))
        }

        OutlinedTextField(
            value = email.value,
            onValueChange = { emailState -> email.value = emailState },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            label = { Text(text = "Enter Your Email") },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedLabelColor = Color.LightGray,
                unfocusedContainerColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                focusedBorderColor = Color.Gray
            )
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { passwordState -> password.value = passwordState },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            label = { Text(text = "Enter Your Password") },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedLabelColor = Color.LightGray,
                unfocusedContainerColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                focusedBorderColor = Color.Gray
            ),
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
                        navController.navigate(
                            Screens.SignUpScreen.route
                        ) {
                            launchSingleTop = true
                        }
                    }
            )
        }
    }
}