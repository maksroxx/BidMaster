package com.roxx.bidmaster.presentation.screens.home

sealed class HomeEvent {
    data class OnBidValueChange(val amount: Int): HomeEvent()
    data class OnSliderValueChange(val sliderValue: Float) : HomeEvent()
    object MakeBid: HomeEvent()
    object DeleteBid: HomeEvent()
    object GoProfile: HomeEvent()
}