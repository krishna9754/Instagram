package com.example.instagram.data

import com.example.instagram.domain.model.User
import com.example.instagram.domain.repo.UserRepository
import com.example.instagram.utils.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val firebaseFireStore: FirebaseFirestore,
) : UserRepository {

    private var opertaionSuccessFul = false

    override fun getUserDetails(userid: String): Flow<Response<User>> = callbackFlow {
        Response.Loading
        val snapShotListner = firebaseFireStore.collection("users")
            .document(userid)
            .addSnapshotListener { snapShot, error ->
                val response = if (snapShot != null) {
                    val userInfo = snapShot.toObject(User::class.java)
                    Response.Success<User>(userInfo!!)
                } else {
                    Response.Error(error?.message ?: error.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapShotListner.remove()
        }
    }

    override fun setUserDetails(
        userid: String,
        name: String,
        userName: String,
        bio: String,
        webSiteUrl: String,
    ): Flow<Response<Boolean>> = flow {
        opertaionSuccessFul = false
        try {
            val userObj = mutableMapOf<String, String>()
            userObj["name"] = name
            userObj["userName"] = userName
            userObj["bio"] = bio
            userObj["websiteUrl"] = webSiteUrl
            firebaseFireStore.collection("users").document(userid)
                .update(userObj as Map<String, Any>)
                .addOnSuccessListener {

                }.await()
            if (opertaionSuccessFul) {
                emit(Response.Success(opertaionSuccessFul))
            }
            else{
                emit(Response.Error("Edit Does Not Success"))
            }
        } catch (e: Exception) {
            Response.Error(e.localizedMessage?:"An Unexpected error")
        }
    }


}