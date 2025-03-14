package com.example.firebaseauthexample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    user: FirebaseUser?,
    onSignOut: () -> Unit,
    onNavigateToAuthentication: () -> Unit
) {
    Scaffold(
        topBar = { WelcomeTopAppBar(onSignOut) }
    )
    { innerPadding ->
        WelcomeContent(
            user,
            onNavigateToAuthentication,
            onSignOut,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun WelcomeContent(
    user: FirebaseUser?,
    onNavigateToAuthentication: () -> Unit,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (user == null) {
            onNavigateToAuthentication()
        } else {
            Text("Welcome ${user.email ?: "unknown"}")
        }

        // TODO logout button in menu
        Button(onClick = { onSignOut() }) {
            Text("Sign out")
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun WelcomeTopAppBar(onSignOut: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text("Welcome") },
        actions = {
            IconButton(onClick = { onSignOut() }) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Log out")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcome() {
    WelcomeScreen(user = null, onSignOut = {}, onNavigateToAuthentication = {})
}