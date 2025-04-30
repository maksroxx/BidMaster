package com.roxx.bidmaster.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.presentation.screens.search.component.ButtonBack
import com.roxx.bidmaster.presentation.screens.search.component.SearchTextField
import com.roxx.bidmaster.presentation.util.UiEvent
import com.roxx.bidmaster.ui.theme.LocalSpacing

@Composable
fun SearchScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val user = viewModel.user.collectAsState().value
    val history = viewModel.searchHistory.collectAsState().value

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalSpacing.current.medium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 140.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = LocalSpacing.current.small),
                text = state.query,
                onValueChange = { viewModel.onEvent(SearchEvent.OnQueryChange(it)) },
                onSearch = { viewModel.onEvent(SearchEvent.OnSearch) },
                onFocusChanged = {
                    viewModel.onEvent(SearchEvent.OnSearchFocusChange(it.isFocused))
                },
                shouldShowHint = state.isHintVisible
            )

            Spacer(modifier = Modifier.height(24.dp))

            when {
                state.isSearching -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        strokeWidth = 4.dp,
                        color = Color.Black
                    )
                }

                user != null -> {
                    UserProfileCard(
                        user = user,
                        onDetailsClick = {
                            viewModel.onEvent(SearchEvent.OnBack)
                        }
                    )
                }

                state.query.isNotEmpty() -> {
                    EmptyStateMessage(
                        onDetailsClick = {
                            viewModel.onEvent(SearchEvent.OnBack)
                        }
                    )
                }

                else -> {
                    ButtonBack({ viewModel.onEvent(SearchEvent.OnBack) }, "Обратно")
                }
            }
        }

        if (history.isNotEmpty() && user == null && !state.isSearching) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(LocalSpacing.current.medium)
            ) {
                Text(
                    text = "История поиска:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(LocalSpacing.current.extraSmall))

                history.forEach { query ->
                    Text(
                        text = query,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                viewModel.onEvent(SearchEvent.OnSelectHistoryItem(query))
                            },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(LocalSpacing.current.small))

                ButtonBack(
                    onButtonClick = { viewModel.onEvent(SearchEvent.OnClearHistory) },
                    text = "Очистить историю"
                )
            }
        }
    }
}


@Composable
private fun UserProfileCard(user: User, onDetailsClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalSpacing.current.medium),
        elevation = CardDefaults.cardElevation(LocalSpacing.current.small),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = user.username,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(LocalSpacing.current.small))

            Text(
                text = "Баланс: ${user.balance}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            ButtonBack(onButtonClick = onDetailsClick, text = "Обратно")
        }
    }
}

@Composable
private fun EmptyStateMessage(onDetailsClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Не найдено",
            modifier = Modifier.size(LocalSpacing.current.extraLarge),
            tint = Color.Black.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        Text(
            text = "Ничего нету",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        ButtonBack(onButtonClick = onDetailsClick, text = "")
    }
}
