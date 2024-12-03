package com.roxx.bidmaster.presentation.screens.login

sealed class LoginEvent {
    object Login: LoginEvent()
    data class OnUsernameChange(val username: String): LoginEvent()
    data class OnPasswordChange(val password: String): LoginEvent()
    object ToRegister: LoginEvent()
}