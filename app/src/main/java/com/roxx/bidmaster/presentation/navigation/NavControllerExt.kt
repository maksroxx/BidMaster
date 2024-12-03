package com.roxx.bidmaster.presentation.navigation

import androidx.navigation.NavController
import com.roxx.bidmaster.presentation.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}