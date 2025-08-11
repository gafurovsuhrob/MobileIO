package com.seros.presentation.ui.main

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.seros.presentation.R
import com.seros.presentation.components.BaseAlertDialog
import com.seros.presentation.components.StatusBarColors
import com.seros.presentation.navigation.Destinations
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = koinViewModel(),
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    val user by mainViewModel.currentUser.collectAsState()
    val context = LocalContext.current

    var lastBackPressTime by remember { mutableLongStateOf(0L) }
    val backPressThreshold = 2000L

    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBackPressTime < backPressThreshold) {
            (context as? android.app.Activity)?.finish()
        } else {
            lastBackPressTime = currentTime
            Toast.makeText(context, "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT).show()
        }
    }

    StatusBarColors(
        statusBarColor = MaterialTheme.colorScheme.primary,
        navBarColor = MaterialTheme.colorScheme.primary
    )

    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.tertiary,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                shape = MaterialTheme.shapes.large,
                onClick = { showLogoutDialog = true }
            ) {
                Icon(
                    painter = painterResource(R.drawable.logout),
                    contentDescription = "Logout",
                    tint = Color.White
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Добро пожаловать, ${user?.name}!",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (showLogoutDialog) {
        BaseAlertDialog(
            title = "Выход из профиля",
            text = "Вы хотите сохранить данные для входа?",
            confirmButtonText = "Сохранить",
            dismissButtonText = "Не сохранять",
            onDismissRequest = { showLogoutDialog = false },
            onConfirm = {
                Toast.makeText(
                    context,
                    "До скорой встречи!",
                    Toast.LENGTH_SHORT
                ).show()
                showLogoutDialog = false
                mainViewModel.logoutLocal()
                navController.navigate(Destinations.Login) {
                    popUpTo(Destinations.Main) { inclusive = true }
                }
            },
            onDismiss = {
                Toast.makeText(
                    context,
                    "Досвидания!",
                    Toast.LENGTH_SHORT
                ).show()
                showLogoutDialog = false
                mainViewModel.logout()
                navController.navigate(Destinations.Login) {
                    popUpTo(Destinations.Main) { inclusive = true }
                }
            }
        )
    }
}