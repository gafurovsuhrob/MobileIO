package com.seros.presentation.ui.auth.forgot_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.seros.presentation.R
import com.seros.presentation.components.AuthTextField
import com.seros.presentation.components.BaseAlertDialog
import com.seros.presentation.components.PrimaryButton
import com.seros.presentation.components.ScreenHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecoverPasswordScreen(
    navController: NavController,
    viewModel: RPViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Назад",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(72.dp)
                .padding(bottom = 24.dp)
        )

        ScreenHeader(
            title = "Forgot Password",
            subtitle = "Enter your email to recover your password!",
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        AuthTextField(
            value = state.email,
            placeholder = "Enter email",
            imeAction = ImeAction.Next,
            errorMessage = state.emailError,
            keyboardType = KeyboardType.Email,
            onValueChange = { viewModel.onEvent(RPEvent.OnEmailChanged(it)) },
            onErrorClear = { viewModel.onEvent(RPEvent.OnEmailErrorCleared) },
        )

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        PrimaryButton(
            text = "Recover Password",
            onClick = {
                viewModel.onEvent(RPEvent.OnRecoverClick)
            },
            isLoading = state.isLoading
        )
    }

    if (state.isRecovered) {
        BaseAlertDialog(
            title = "Forgot Password?",
            text = "We will send you a link to reset your password. Please check your email.",
            onDismissRequest = { viewModel.onEvent(RPEvent.OnDialogDismiss) },
            onConfirm = { viewModel.onEvent(RPEvent.OnDialogDismiss) }
        )
    }
}
