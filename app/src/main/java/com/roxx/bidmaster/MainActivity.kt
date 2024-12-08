package com.roxx.bidmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.roxx.bidmaster.presentation.navigation.Routes
import com.roxx.bidmaster.presentation.navigation.navigate
import com.roxx.bidmaster.presentation.screens.home.HomeScreen
import com.roxx.bidmaster.presentation.screens.login.LoginScreen
import com.roxx.bidmaster.presentation.screens.profile.ProfileScreen
import com.roxx.bidmaster.presentation.screens.register.RegisterScreen
import com.roxx.bidmaster.presentation.screens.welcome.WelcomeScreen
import com.roxx.bidmaster.ui.theme.BidMasterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            BidMasterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White,
                    snackbarHost = { SnackbarHost(snackBarHostState) },
                    content = {
                        NavHost(
                            modifier = Modifier.padding(it),
                            navController = navController,
                            startDestination = Routes.LOGO
                        ) {
                            composable(Routes.LOGO) {
                                WelcomeScreen(
                                    onNavigate = navController::navigate
                                )
                            }
                            composable(Routes.LOGIN) {
                                LoginScreen(
                                    snackBarHostState = snackBarHostState,
                                    onNavigate = navController::navigate
                                )
                            }
                            composable(Routes.REGISTER) {
                                RegisterScreen(
                                    snackBarHostState = snackBarHostState,
                                    onNavigate = navController::navigate
                                )
                            }
                            composable(Routes.HOME) {
                                HomeScreen(
                                    snackBarHostState = snackBarHostState,
                                    onNavigate = navController::navigate
                                )
                            }
                            composable(Routes.PROFILE) {
                                ProfileScreen(
                                    snackBarHostState = snackBarHostState,
                                    onNavigate = navController::navigate
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}