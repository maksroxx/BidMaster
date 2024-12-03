package com.roxx.bidmaster.presentation.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roxx.bidmaster.presentation.util.UiEvent

@Composable
fun LoginScreen(
    snackBarHostState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
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
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Поле ввода имени пользователя
            TextField(
                value = viewModel.username,
                onValueChange = { viewModel.onEvent(LoginEvent.OnUsernameChange(it)) },
                label = { Text("Имя пользователя") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Поле ввода пароля
            TextField(
                value = viewModel.password,
                onValueChange = { viewModel.onEvent(LoginEvent.OnPasswordChange(it)) },
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка "Войти"
            Button(
                onClick = { viewModel.onEvent(LoginEvent.Login) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Войти")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Кнопка "Зарегистрироваться"
            Text(
                text = "Нет аккаунта? Зарегистрируйтесь",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { viewModel.onEvent(LoginEvent.ToRegister) }
                    .padding(8.dp)
            )
        }
    }
}