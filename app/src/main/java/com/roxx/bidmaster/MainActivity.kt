package com.roxx.bidmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.roxx.bidmaster.presentation.RegisterViewModel
import com.roxx.bidmaster.ui.theme.BidMasterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<RegisterViewModel>()
            BidMasterTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Button(
                        content = {
                            Text("Click")
                        },
                        modifier = Modifier.align(Alignment.Center),
                        onClick = { viewModel.onClick() })
                }
            }
        }
    }
}