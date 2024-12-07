package com.roxx.bidmaster.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.use_case.ValidateTokenUseCase
import com.roxx.bidmaster.presentation.navigation.Routes
import com.roxx.bidmaster.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val validateTokenUseCase: ValidateTokenUseCase
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            when(val result = validateTokenUseCase()){
                is Result.Error -> {
                    _uiEvent.send(UiEvent.Navigate(Routes.LOGIN))
                }
                is Result.Success -> {
                    _uiEvent.send(UiEvent.Navigate(Routes.HOME))
                }
            }
        }
    }
}