package com.roxx.bidmaster.presentation.screens.register

sealed class RegisterEvent {
    object Register: RegisterEvent()
    data class OnUsernameChange(val username: String): RegisterEvent()
    data class OnPasswordChange(val password: String): RegisterEvent()
    object ToLogin: RegisterEvent()
}