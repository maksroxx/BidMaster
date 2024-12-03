package com.roxx.bidmaster.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roxx.bidmaster.data.model.UserRequest
import com.roxx.bidmaster.domain.use_case.CreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
): ViewModel() {
    fun onClick() {
        viewModelScope.launch {
            createUserUseCase.invoke(UserRequest("AA", "password"))
        }
    }
}