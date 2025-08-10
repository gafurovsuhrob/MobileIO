package com.seros.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.seros.presentation.ui.auth.forgot_password.RecoverPasswordScreen
import com.seros.presentation.ui.auth.login.LoginScreen
import com.seros.presentation.ui.auth.register.RegisterScreen
import com.seros.presentation.ui.main.MainScreen
import com.seros.presentation.ui.splash.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = Destinations.Splash
    ) {

        composable<Destinations.Splash> {
            SplashScreen { isLoggedIn ->
                val nextDestination = if (isLoggedIn) {
                    Destinations.Main
                } else {
                    Destinations.Login
                }
                navController.navigate(nextDestination) {
                    popUpTo(Destinations.Splash) { inclusive = true }
                }
            }
        }

        composable<Destinations.Login> {
            LoginScreen(navController = navController)
        }

        composable<Destinations.Register> {
            RegisterScreen(navController = navController)
        }

        composable<Destinations.Main> {
            MainScreen(navController = navController)
        }

        composable<Destinations.RecoverPassword> {
            RecoverPasswordScreen(navController = navController)
        }
    }
}

