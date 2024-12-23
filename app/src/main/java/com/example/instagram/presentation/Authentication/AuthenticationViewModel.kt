package com.example.instagram.presentation.Authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagram.domain.use_case.Authenticatio_use_Cases.AuthenticationUseCases
import com.example.instagram.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationUseCases: AuthenticationUseCases
) : ViewModel() {

    val isUserAuthentication: Boolean
        get() = authenticationUseCases.isUserAuthentication()

    private val _signInState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signInState: State<Response<Boolean>> = _signInState

    private val _signUpState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signUpState: State<Response<Boolean>> = _signUpState

    private val _signOutState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signOutState: State<Response<Boolean>> = _signOutState

    private val _firebaseAuth = mutableStateOf<Boolean>(false)
    val firebaseAuth: State<Boolean> = _firebaseAuth


    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authenticationUseCases.firebaseSignIn(email = email, password = password).collect {
                _signInState.value = it
            }
        }
    }

    fun signUp(email: String, password: String, userName: String) {
        viewModelScope.launch {
            authenticationUseCases.firebaseSignUp(email = email, password = password, userName = userName).collect {
                _signUpState.value = it
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authenticationUseCases.firebaseSignOut().collect {
                _signOutState.value = it
                if (it == Response.Success(true)) {
                    _signInState.value = Response.Success(false)
                }
            }
        }
    }

    fun firebaseAuth() {
        viewModelScope.launch {
            authenticationUseCases.firebaseAuthState().collect {
                _firebaseAuth.value = it
            }
        }
    }
}
