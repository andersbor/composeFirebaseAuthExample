package com.example.firebaseauthexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauthexample.screens.Authentication
import com.example.firebaseauthexample.screens.Welcome
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
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val authenticationViewModel: AuthenticationViewModel = viewModel()

    NavHost(navController = navController, startDestination = NavRoutes.Authentication.route) {
        composable(NavRoutes.Authentication.route) {
            Authentication(
                modifier,
                user = authenticationViewModel.user,
                message = authenticationViewModel.message,
                signIn = { email, password -> authenticationViewModel.signIn(email, password) },
                register = { email, password -> authenticationViewModel.register(email, password) },
                navigateToWelcome = { navController.navigate(NavRoutes.Welcome.route) }
            )
        }
        composable(NavRoutes.Welcome.route) {
            Welcome(
                modifier,
                user = authenticationViewModel.user,
                signOut = { authenticationViewModel.signOut() },
                navigateToAuthentication = {
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
