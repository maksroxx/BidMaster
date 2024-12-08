package com.roxx.bidmaster.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.domain.use_case.GetBidsUseCase
import com.roxx.bidmaster.domain.use_case.GetMyInformationUseCase
import com.roxx.bidmaster.presentation.navigation.Routes
import com.roxx.bidmaster.presentation.util.UiEvent
import com.roxx.bidmaster.presentation.util.UiText
import com.roxx.bidmaster.presentation.util.toBidUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMyInformationUseCase: GetMyInformationUseCase,
    private val getBidsUseCase: GetBidsUseCase
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _bids = MutableStateFlow<List<BidUi>>(emptyList())
    val bids: StateFlow<List<BidUi>> = _bids

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            when (val result = getMyInformationUseCase()) {
                is Result.Error -> {
                    result.message?.let {
                        _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(result.message)))
                    }
                }

                is Result.Success -> {
                    result.data?.let {
                        _user.value = result.data
                    }
                }
            }
            when (val result = getBidsUseCase()) {
                is Result.Error -> {
                    result.message?.let {
                        _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(result.message)))
                    }
                }

                is Result.Success -> {
                    result.data?.let {
                        _bids.value = result.data.map { it.toBidUi() }
                    }
                }
            }
        }
    }

    fun onClick() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate(Routes.HOME))
        }
    }
}