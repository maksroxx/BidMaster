package com.roxx.bidmaster.presentation.screens.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.roxx.bidmaster.presentation.screens.login.LoginEvent
import com.roxx.bidmaster.presentation.util.UiEvent
import com.roxx.bidmaster.ui.theme.LocalSpacing

@Composable
fun RegisterScreen(
    snackBarHostState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val enabledButton = viewModel.password.isNotEmpty() && viewModel.username.isNotEmpty()
    val localSpacing = LocalSpacing.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }

                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(localSpacing.large),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewModel.username,
                onValueChange = { viewModel.onEvent(RegisterEvent.OnUsernameChange(it)) },
                label = { Text("Username") },
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "User Icon")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(localSpacing.medium))

            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onEvent(RegisterEvent.OnPasswordChange(it)) },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Password Icon")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(localSpacing.large))

            Button(
                onClick = { viewModel.onEvent(RegisterEvent.Register) },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabledButton
            ) {
                Text(text = "Register")
            }

            Spacer(modifier = Modifier.height(localSpacing.small))

            Text(
                text = "Have account? Login",
                color = Color.Black,
                modifier = Modifier
                    .clickable { viewModel.onEvent(RegisterEvent.ToLogin) }
            )
        }
    }
}