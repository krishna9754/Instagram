package com.example.instagram.domain.use_case.user_useCases

import com.example.instagram.domain.repo.UserRepository
import javax.inject.Inject

class SetUserDetails @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(
        userid: String,
        name: String,
        bio: String,
        webSiteUrl: String,
        userName: String,
    ) =
        repository.setUserDetails(
            userid = userid,
            userName = userName,
            bio = bio,
            webSiteUrl = webSiteUrl,
            name = name
        )
}