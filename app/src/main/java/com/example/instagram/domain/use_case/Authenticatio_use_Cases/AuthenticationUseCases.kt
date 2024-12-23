package com.example.instagram.domain.use_case.Authenticatio_use_Cases

data class AuthenticationUseCases (
    var isUserAuthentication: IsUserAuthentication,
    var firebaseAuthState: FirebaseAuthState,
    var firebaseSignIn: FirebaseSignIn,
    var firebaseSignUp: FirebaseSignUp,
    var firebaseSignOut: FirebaseSignOut
)