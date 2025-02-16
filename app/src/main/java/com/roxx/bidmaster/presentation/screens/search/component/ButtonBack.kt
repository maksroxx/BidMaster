package com.roxx.bidmaster.presentation.screens.search.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.roxx.bidmaster.ui.theme.LocalSpacing

@Composable
fun ButtonBack(onButtonClick: () -> Unit, text: String) {
    Button(
        onClick = onButtonClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color.Black.copy(alpha = 0.7f)
        ),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(48.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Details",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(LocalSpacing.current.small))
        Text(text)
    }
}
