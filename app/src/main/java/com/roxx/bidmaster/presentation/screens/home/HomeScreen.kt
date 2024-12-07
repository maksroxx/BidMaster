package com.roxx.bidmaster.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roxx.bidmaster.presentation.util.UiEvent
import com.roxx.bidmaster.ui.theme.LocalSpacing

@Composable
fun HomeScreen(
    snackBarHostState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val localSpacing = LocalSpacing.current
    val bidState by viewModel.bidState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }

                else -> Unit
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(localSpacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.Gray)
                    .padding(localSpacing.small)
            ) {
                Text(text = "Balance: ${viewModel.balance}", color = Color.White)
            }
            IconButton(onClick = { viewModel.onEvent(HomeEvent.GoProfile) }) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "User Icon")
            }
        }

        Spacer(modifier = Modifier.height(localSpacing.large))

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = localSpacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (!bidState) {
                Text(text = "Balance: ${viewModel.amount}", fontSize = 24.sp)

                Spacer(modifier = Modifier.height(localSpacing.small))

                Slider(
                    value = viewModel.sliderValue,
                    onValueChange = { value ->
                        viewModel.onEvent(HomeEvent.OnSliderValueChange(value))
                    },
                    modifier = Modifier.padding(vertical = localSpacing.medium)
                )

                Spacer(modifier = Modifier.height(localSpacing.medium))
            }

            Button(
                onClick = {
                    if (bidState) {
                        viewModel.onEvent(HomeEvent.DeleteBid)
                    } else {
                        viewModel.onEvent(HomeEvent.MakeBid)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = if (bidState) true else viewModel.amount > 0
            ) {
                Text(text = if (bidState) "Delete a bid" else "Place a bid")
            }
        }
    }
}