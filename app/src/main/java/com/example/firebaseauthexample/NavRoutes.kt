package com.example.firebaseauthexample

sealed class NavRoutes(val route: String) {
    data object Authentication : NavRoutes("authentication")
    data object Welcome : NavRoutes("welcome")
}