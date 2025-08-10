package com.seros.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.navigation.compose.rememberNavController
import com.seros.presentation.navigation.AppNavHost
import org.koin.compose.KoinContext

@Composable
fun MobileIOApp(
    modifier: Modifier = Modifier
) {
    KoinContext {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.47f),
                                    MaterialTheme.colorScheme.background
                                ),
                                startY = 20f,
                                endY = 360f
                            )
                        )
                        .padding(innerPadding)
                ) {
                    AppNavHost(navController = navController)
                }
            }
        )
    }
}
