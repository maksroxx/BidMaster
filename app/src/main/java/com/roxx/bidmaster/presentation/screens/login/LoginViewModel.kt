package com.roxx.bidmaster.presentation.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.use_case.LoginUserUseCase
import com.roxx.bidmaster.presentation.navigation.Routes
import com.roxx.bidmaster.presentation.util.UiEvent
import com.roxx.bidmaster.presentation.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {
    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                viewModelScope.launch {
                    when (val result = loginUserUseCase.invoke(username, password)) {
                        is Result.Error -> {
                            result.message?.let {
                                _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(result.message)))
                            }
                        }

                        is Result.Success -> {
                            result.data?.let {
                                _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(result.data)))
                                _uiEvent.send(UiEvent.Navigate(Routes.HOME))
                            }
                        }
                    }
                }
            }

            is LoginEvent.ToRegister -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Routes.REGISTER))
                }
            }

            is LoginEvent.OnPasswordChange -> {
                password = event.password.trim()
            }
            is LoginEvent.OnUsernameChange -> {
                username = event.username.trim()
            }
        }
    }
}