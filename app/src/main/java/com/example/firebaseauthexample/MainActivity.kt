package com.example.firebaseauthexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauthexample.screens.AuthenticationScreen
import com.example.firebaseauthexample.screens.WelcomeScreen
import com.example.firebaseauthexample.ui.theme.FirebaseAuthExampleTheme

// project connected to Firebase Birdwatching project

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseAuthExampleTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Authentication.route
    ) {
        composable(NavRoutes.Authentication.route) {
            AuthenticationScreen(
                user = authenticationViewModel.user,
                message = authenticationViewModel.message,
                signIn = { email, password -> authenticationViewModel.signIn(email, password) },
                register = authenticationViewModel::register,
                navigateToNextScreen = { navController.navigate(NavRoutes.Welcome.route) }
            )
        }
        composable(NavRoutes.Welcome.route) {
            WelcomeScreen(
                user = authenticationViewModel.user,
                onSignOut = { authenticationViewModel.signOut() },
                onNavigateToAuthentication = {
                    navController.popBackStack(NavRoutes.Authentication.route, inclusive = false)
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FirebaseAuthExampleTheme {
        MainScreen()
    }
}
