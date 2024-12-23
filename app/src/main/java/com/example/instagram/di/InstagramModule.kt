package com.example.instagram.di

import com.example.instagram.data.AuthenticationRepoImp
import com.example.instagram.data.UserRepositoryImp
import com.example.instagram.domain.repo.AuthenticationRepo
import com.example.instagram.domain.repo.UserRepository
import com.example.instagram.domain.use_case.Authenticatio_use_Cases.AuthenticationUseCases
import com.example.instagram.domain.use_case.Authenticatio_use_Cases.FirebaseAuthState
import com.example.instagram.domain.use_case.Authenticatio_use_Cases.FirebaseSignIn
import com.example.instagram.domain.use_case.Authenticatio_use_Cases.FirebaseSignOut
import com.example.instagram.domain.use_case.Authenticatio_use_Cases.FirebaseSignUp
import com.example.instagram.domain.use_case.Authenticatio_use_Cases.IsUserAuthentication
import com.example.instagram.domain.use_case.user_useCases.GetUserDetails
import com.example.instagram.domain.use_case.user_useCases.SetUserDetails
import com.example.instagram.domain.use_case.user_useCases.UserUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
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

    @Singleton
    @Provides
    fun provideUserRepo(firebaseFireStore: FirebaseFirestore) : UserRepository{
        return UserRepositoryImp(firebaseFireStore = firebaseFireStore)
    }

    @Singleton
    @Provides
    fun provideUserUseCase(repository: UserRepository): UserUseCase {
        return UserUseCase(
            getUserDetails = GetUserDetails(repository = repository),
            setUserDetails = SetUserDetails(repository = repository)
        )
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationModule {

    // Use @Binds to bind AuthenticationRepo to its implementation
    @Singleton
    @Binds
    abstract fun bindAuthenticationRepo(authRepoImp: AuthenticationRepoImp): AuthenticationRepo
}
