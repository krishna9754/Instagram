package com.example.instagram.domain.repo

import com.example.instagram.utils.Response
import dagger.Provides
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepo {

    fun isUserAuthenticationFirebase() : Boolean

    fun getFirebaseAuthState() : Flow<Boolean>

    fun firebaseSignIn(email: String, password: String) : Flow<Response<Boolean>>

    fun firebaseSignOut() : Flow<Response<Boolean>>

    fun firebaseSignUp(email: String, password: String, userName: String) : Flow<Response<Boolean>>

}