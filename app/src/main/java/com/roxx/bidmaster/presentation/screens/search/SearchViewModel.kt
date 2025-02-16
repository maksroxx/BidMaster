package com.roxx.bidmaster.presentation.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.domain.use_case.SearchUserUseCase
import com.roxx.bidmaster.presentation.navigation.Routes
import com.roxx.bidmaster.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel() {
    var state by mutableStateOf(SearchState())
        private set

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChange -> {
                state = state.copy(query = event.query)
            }

            is SearchEvent.OnSearch -> {
                executeSearch()
            }

            is SearchEvent.OnSearchFocusChange -> {
                state = state.copy(
                    isHintVisible = !event.isFocused && state.query.isBlank()
                )
            }

            is SearchEvent.OnBack -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Routes.PROFILE))
                }
            }
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            state = state.copy(
                isSearching = true
            )
            _user.value = null
            when (val result = searchUserUseCase.invoke(state.query)) {
                is Result.Error -> {
                    state = state.copy(
                        isSearching = false,
                        query = ""
                    )
                }

                is Result.Success -> {
                    result.data?.let {
                        _user.value = result.data
                        state = state.copy(
                            isSearching = false,
                            query = ""
                        )
                    }
                }
            }
        }
    }
}