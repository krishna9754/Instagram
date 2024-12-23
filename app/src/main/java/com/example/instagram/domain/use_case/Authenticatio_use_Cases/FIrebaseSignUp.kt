package com.example.instagram.domain.use_case.Authenticatio_use_Cases

import com.example.instagram.domain.repo.AuthenticationRepo
import javax.inject.Inject

class FirebaseSignUp @Inject constructor(
    private val repo: AuthenticationRepo
){
    operator fun invoke(email: String, password: String, userName: String) = repo.firebaseSignUp(email, password, userName)
}