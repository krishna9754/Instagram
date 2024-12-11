package com.example.instagram.di

import com.example.instagram.data.AuthenticationRepoImp
import com.example.instagram.domain.repo.AuthenticationRepo
import com.example.instagram.domain.use_case.AuthenticationUseCases
import com.example.instagram.domain.use_case.FirebaseAuthState
import com.example.instagram.domain.use_case.FirebaseSignIn
import com.example.instagram.domain.use_case.FirebaseSignOut
import com.example.instagram.domain.use_case.FirebaseSignUp
import com.example.instagram.domain.use_case.IsUserAuthentication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InstagramModule {

    @Singleton
    @Provides
    fun provideFirebaseAuthentication(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Singleton
    @Provides
    fun provideAuthenticationRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): AuthenticationRepoImp{
        return AuthenticationRepoImp(auth = auth, firestore = firestore)
    }

    @Singleton
    @Provides
    fun provideAuthUseCases(repo: AuthenticationRepo) = AuthenticationUseCases(
        isUserAuthentication = IsUserAuthentication(repo = repo),
        firebaseAuthState = FirebaseAuthState(repo = repo),
        firebaseSignIn = FirebaseSignIn(repo = repo),
        firebaseSignUp = FirebaseSignUp(repo = repo),
        firebaseSignOut = FirebaseSignOut(repo = repo)
    )
}
