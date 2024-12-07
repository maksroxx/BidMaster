package com.roxx.bidmaster.presentation.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.use_case.BidStateUseCase
import com.roxx.bidmaster.domain.use_case.DeleteBidUseCase
import com.roxx.bidmaster.domain.use_case.GetLastBidUseCase
import com.roxx.bidmaster.domain.use_case.GetMyInformationUseCase
import com.roxx.bidmaster.domain.use_case.MakeBidUseCase
import com.roxx.bidmaster.presentation.navigation.Routes
import com.roxx.bidmaster.presentation.util.UiEvent
import com.roxx.bidmaster.presentation.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bidUseCase: MakeBidUseCase,
    private val deleteBidUseCase: DeleteBidUseCase,
    private val bidStateUseCase: BidStateUseCase,
    private val getMyInformationUseCase: GetMyInformationUseCase,
    private val getLastBidUseCase: GetLastBidUseCase
) : ViewModel() {
    var balance by mutableStateOf(0)
        private set

    var amount by mutableStateOf(0)
        private set

    private val _bidState = MutableStateFlow(false)
    val bidState: StateFlow<Boolean> = _bidState

    var sliderValue by mutableStateOf(0f)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            val lastBidDeferred = async { getLastBidUseCase() }
            lastBidDeferred.await()
            when(val result = getMyInformationUseCase()) {
                is Result.Error -> {
                    if(result.redirectToLogin) {
                        _uiEvent.send(UiEvent.Navigate(Routes.LOGIN))
                    }
                    result.message?.let {
                        _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(result.message)))
                    }
                }
                is Result.Success -> {
                    result.data?.let {
                        balance = result.data.balance
                    }
                }
            }
            _bidState.value = bidStateUseCase()
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.DeleteBid -> {
                viewModelScope.launch {
                    when(val result = deleteBidUseCase()) {
                        is Result.Error -> {
                            if(result.redirectToLogin) {
                                _uiEvent.send(UiEvent.Navigate(Routes.LOGIN))
                            }
                            result.message?.let {
                                _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(result.message)))
                            }
                        }
                        is Result.Success -> {
                            result.data?.let {
                                balance = result.data.amount
                            }
                        }
                    }
                    _bidState.value = bidStateUseCase()
                }
            }

            is HomeEvent.GoProfile -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Routes.PROFILE))
                }
            }

            is HomeEvent.MakeBid -> {
                viewModelScope.launch {
                    when(val result = bidUseCase(amount)) {
                        is Result.Error -> {
                            if(result.redirectToLogin) {
                                _uiEvent.send(UiEvent.Navigate(Routes.LOGIN))
                            }
                            result.message?.let {
                                _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(result.message)))
                            }
                        }
                        is Result.Success -> {
                            result.data?.let {
                                balance = result.data.balance
                            }
                        }
                    }
                    _bidState.value = bidStateUseCase()
                }
            }

            is HomeEvent.OnBidValueChange -> {
                if (event.amount <= balance) {
                    amount = event.amount
                }
            }

            is HomeEvent.OnSliderValueChange -> {
                sliderValue = event.sliderValue
                amount = (sliderValue * balance).toInt()
            }
        }
    }
}