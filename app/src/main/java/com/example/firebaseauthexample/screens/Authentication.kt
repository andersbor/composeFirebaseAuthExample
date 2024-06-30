package com.example.firebaseauthexample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Authentication(
    modifier: Modifier = Modifier,
    user: FirebaseUser? = null,
    message: String = "",
    signIn: (email: String, password: String) -> Unit = { _, _ -> },
    register: (email: String, password: String) -> Unit = { _, _ -> },
    navigateToWelcome: () -> Unit = {}
) {
    if (user != null) {
        navigateToWelcome()
    }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailIsError by remember { mutableStateOf(false) }
    var passwordIsError by remember { mutableStateOf(false) }
    val showPassword = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Authentication") }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // TODO layout for landscape: side by side
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                isError = emailIsError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            if (emailIsError) {
                Text("Invalid email", color = MaterialTheme.colorScheme.error)
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation =
                if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                isError = passwordIsError,
                trailingIcon = {
                    IconButton(onClick = { showPassword.value = !showPassword.value }) {
                        if (showPassword.value) {
                            Icon(Icons.Filled.Visibility, contentDescription = "Hide password")
                            // icons: large packet of icons loaded in gradle file
                        } else {
                            Icon(Icons.Filled.VisibilityOff, contentDescription = "Show password")
                        }
                    }
                }
            )
            if (passwordIsError) {
                Text("Invalid password", color = MaterialTheme.colorScheme.error)
            }
            if (message.isNotEmpty()) {
                Text(message, color = MaterialTheme.colorScheme.error)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    // TODO same validation for register and sign in
                    register(email, password)
                }) {
                    Text("Register")
                }
                Button(onClick = {
                    email = email.trim()
                    if (email.isEmpty() || !validateEmail(email)) {
                        emailIsError = true
                        return@Button
                    } else {
                        emailIsError = false
                    }
                    password = password.trim()
                    if (password.isEmpty()) {
                        passwordIsError = true
                        return@Button
                    } else {
                        passwordIsError = false
                    }
                    signIn(email, password)
                }) {
                    Text("Sign in")
                }
            }
        }
    }
}

fun validateEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun AuthPreview() {
    Authentication()
}