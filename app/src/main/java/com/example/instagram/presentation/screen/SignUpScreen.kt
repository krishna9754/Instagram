package com.example.instagram.presentation.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.instagram.R
import com.example.instagram.mode.ThemeSetting
import com.example.instagram.presentation.Authentication.AuthenticationViewModel
import com.example.instagram.presentation.Toast
import com.example.instagram.utils.Response
import com.example.instagram.utils.Screens

@Composable
fun SignUpScreen(
    navController: NavHostController,
    authenticationViewModel: AuthenticationViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val userName = remember { mutableStateOf("") }
        val buttonEnableAndDisabled = remember { mutableStateOf(false) }
        val colorPick = if (buttonEnableAndDisabled.value) Color(0xff1877F2) else Color(0xff7faefa)

        Image(
            painter = painterResource(id = R.drawable.instagram),
            contentDescription = "LogIn Screen",
            modifier = Modifier.width(220.dp),
            colorFilter = ColorFilter.tint(Color.Black)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Sign up to see photos and videos from your friends.",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            onClick = { },
            colors = ButtonDefaults.buttonColors(Color(0xff1877F2))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "LogIn Screen",
                    modifier = Modifier.size(30.dp)
                )
                Text(text = "Continue with Facebook", fontSize = 17.sp)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(modifier = Modifier.width(130.dp))
            Text(text = "OR")
            HorizontalDivider(modifier = Modifier.width(130.dp))
        }
        OutlinedTextField(
            value = userName.value,
            onValueChange = { userState -> userName.value = userState },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Enter User Name", color = Color.LightGray) },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedLabelColor = Color.LightGray,
                unfocusedContainerColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                focusedBorderColor = Color.LightGray
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { emailState -> email.value = emailState },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = "Enter Your Email", color = Color.LightGray) },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedLabelColor = Color.LightGray,
                unfocusedContainerColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                focusedBorderColor = Color.LightGray
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { passwordState -> password.value = passwordState },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = "Enter Your Password", color = Color.LightGray) },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedLabelColor = Color.LightGray,
                unfocusedContainerColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                focusedBorderColor = Color.LightGray
            ),
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(15.dp))

        Text(text = "By signing up, you agree to Our Terms", color = Color.Gray)
        Text(text = "Privacy Policy and Cookies", color = Color.Blue)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            shape = RoundedCornerShape(10.dp),
            onClick = {
                authenticationViewModel.signUp(
                    email = email.value,
                    password = password.value,
                    userName = userName.value
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = buttonEnableAndDisabled.value,
            colors = ButtonDefaults.buttonColors(colorPick)
        ) {

            Text(text = "Sign Up")
            when (val response = authenticationViewModel.signUpState.value) {
                is Response.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                }

                is Response.Success -> {
                    if (response.data) {
                        LaunchedEffect(key1 = true) {
                            navController.navigate(Screens.ProfileScreen.route) {
                                popUpTo(Screens.LogInScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    } else {
                        Toast(message = "SignUp Failed")
                    }
                }

                is Response.Error -> {
                    Toast(message = response.message)
                }
            }

        }
        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = "Have an account?",
                color = Color(0xffAEA9A9),
            )

            Text(
                text = "Sign In",
                color = Color(0xff267FF3),
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(
                            Screens.LogInScreen.route
                        ) {
                            launchSingleTop = true
                        }
                    }
            )
        }
    }
}