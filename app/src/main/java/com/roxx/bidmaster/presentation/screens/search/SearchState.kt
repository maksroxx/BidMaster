package com.roxx.bidmaster.presentation.screens.search

data class SearchState(
    val query: String = "",
    val isHintVisible: Boolean = false,
    val isSearching: Boolean = false
)
