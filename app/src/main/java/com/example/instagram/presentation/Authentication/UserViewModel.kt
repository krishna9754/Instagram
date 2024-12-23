package com.example.instagram.presentation.Authentication

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagram.domain.repo.UserRepository
import com.example.instagram.domain.use_case.user_useCases.UserUseCase
import com.example.instagram.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val userID = auth.currentUser?.uid
    private val _getUserData = mutableStateOf<Response<User?>>(Response.Success(null))
     @SuppressLint("RestrictedApi")
     val getUserDate : State<Response<User?>> = _getUserData

    fun getUserInfo(){
        if(userID!=null){
            viewModelScope.launch {
                userUseCase.getUserDetails(userID).collect{
//                    _getUserData.value = it
                }
            }
        }
    }
}