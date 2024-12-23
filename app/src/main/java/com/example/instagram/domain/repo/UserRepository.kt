package com.example.instagram.domain.repo

import com.example.instagram.domain.model.User
import com.example.instagram.utils.Response
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserDetails(userid: String) : Flow<Response<User>>
    fun setUserDetails(
        userid: String,
        name: String,
        userName: String,
        bio: String,
        webSiteUrl: String
    ) : Flow<Response<Boolean>>
}