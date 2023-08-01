package com.yvkalume.eventcademy.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yvkalume.eventcademy.ui.navigation.Destination
import com.yvkalume.eventcademy.ui.screen.eventdetail.EventDetailRoute
import com.yvkalume.eventcademy.ui.screen.home.HomeRoute
import com.yvkalume.eventcademy.ui.theme.EventCademyTheme
import com.yvkalume.eventcademy.util.navigate

class MainActivity : ComponentActivity() {
    @SuppressLint("MaterialDesignInsteadOrbitDesign")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            EventCademyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        val navController = rememberNavController()
                        NavHost(
                            modifier = Modifier.padding(it),
                            navController = navController,
                            startDestination = Destination.HomeScreen.route
                        ) {
                            composable(route = Destination.HomeScreen.route) {
                                HomeRoute(
                                    onEventClick = { navController.navigate(Destination.EventDetailScreen) }
                                )
                            }

                            composable(route = Destination.EventDetailScreen.route) {
                                EventDetailRoute(onBackClick = navController::navigateUp)
                            }
                        }
                    }
                }
            }
        }
    }
}