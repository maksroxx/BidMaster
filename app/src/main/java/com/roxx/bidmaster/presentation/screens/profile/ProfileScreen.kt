package com.roxx.bidmaster.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roxx.bidmaster.presentation.util.UiEvent
import com.roxx.bidmaster.ui.theme.DarkGreen
import com.roxx.bidmaster.ui.theme.LocalSpacing

@Composable
fun ProfileScreen(
    snackBarHostState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val localSpacing = LocalSpacing.current
    val user by viewModel.user.collectAsState()
    val bids by viewModel.bids.collectAsState()
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
        IconButton(
            onClick = { viewModel.onClick() },
            modifier = Modifier
                .align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(localSpacing.medium))

        user?.let {
            Text(
                text = "Welcome ${it.username}",
                style = MaterialTheme.typography.displaySmall,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(localSpacing.medium))

            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.DarkGray)
                    .padding(localSpacing.small),
                color = Color.White,
                text = "Balance: ${it.balance}",
                style = MaterialTheme.typography.bodyLarge
            )
        } ?: run {
            Text(
                text = "Uploading user information...",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(localSpacing.large))

        if (bids.isEmpty()) {
            Text(
                text = "No bids",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            )
        } else {
            Text(
                modifier = Modifier.fillMaxWidth().padding(start = localSpacing.small),
                textAlign = TextAlign.Start,
                text = "Bids",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(localSpacing.small))
            LazyColumn {
                items(bids) { bid ->
                    BidItem(bid)
                }
            }
        }
    }
}

@Composable
fun BidItem(bid: BidUi) {
    val localSpacing = LocalSpacing.current
    val backgroundColor = when {
        bid.profit > 0 -> DarkGreen
        bid.profit < 0 -> Color.Red.copy(alpha = 0.8f)
        else -> {
            Color.DarkGray.copy(alpha = 0.9f)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(localSpacing.medium)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Bid ID: ${bid.id}", style = MaterialTheme.typography.bodyLarge)
                Text(text = bid.createdAt, style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.height(localSpacing.medium))
            Text(text = "Amount: ${bid.amount}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(localSpacing.medium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Profit: ${bid.profit}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Status: ${bid.status}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}