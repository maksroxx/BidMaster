package com.roxx.bidmaster.presentation.screens.search

sealed class SearchEvent {
    data class OnQueryChange(val query: String): SearchEvent()
    object OnSearch: SearchEvent()
    object OnBack: SearchEvent()
    data class OnSearchFocusChange(val isFocused: Boolean): SearchEvent()
}