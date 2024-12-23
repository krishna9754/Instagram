package com.example.instagram.domain.use_case.user_useCases

import com.example.instagram.domain.repo.UserRepository
import javax.inject.Inject

class GetUserDetails @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userid: String) = repository.getUserDetails(userid = userid)
}