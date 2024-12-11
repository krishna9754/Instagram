package com.example.instagram.domain.use_case

import com.example.instagram.domain.repo.AuthenticationRepo
import javax.inject.Inject

class FirebaseSignIn @Inject constructor(
    private val repo: AuthenticationRepo
){
    operator fun invoke(email: String, password: String) = repo.firebaseSignIn(email, password)
}