package com.seros.presentation.ui.auth.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.seros.presentation.components.AuthTextField
import com.seros.presentation.components.DatePickerField
import com.seros.presentation.components.PrimaryButton
import com.seros.presentation.components.ScreenHeader
import com.seros.presentation.navigation.Destinations
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.isRegistered) {
        if (state.isRegistered) {
            Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
            navController.navigate(Destinations.Login) {
                popUpTo(Destinations.Register) { inclusive = true }
            }
        }
    }

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

        ScreenHeader(
            title = "Register",
            subtitle = "Create an account to continue!",
        )

        AuthTextField(
            value = state.username,
            onValueChange = { viewModel.onEvent(RegisterEvent.OnUsernameChanged(it)) },
            placeholder = "Enter username",
            imeAction = ImeAction.Next,
            errorMessage = state.usernameError,
            onErrorClear = { viewModel.onEvent(RegisterEvent.OnUsernameErrorCleared) },
        )

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        AuthTextField(
            value = state.name,
            onValueChange = { viewModel.onEvent(RegisterEvent.OnNameChanged(it)) },
            placeholder = "Enter name",
            imeAction = ImeAction.Next,
            errorMessage = state.nameError,
            onErrorClear = { viewModel.onEvent(RegisterEvent.OnNameErrorCleared) },
        )

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        AuthTextField(
            value = state.email,
            placeholder = "Enter email",
            imeAction = ImeAction.Next,
            errorMessage = state.emailError,
            keyboardType = KeyboardType.Email,
            onValueChange = { viewModel.onEvent(RegisterEvent.OnEmailChanged(it)) },
            onErrorClear = { viewModel.onEvent(RegisterEvent.OnEmailErrorCleared) },
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        DatePickerField(
            value = state.dateOfBirth,
            errorMessage = state.dateOfBirthError,
            onDateSelected = { viewModel.onEvent(RegisterEvent.OnDateOfBirthChanged(it)) },
            onValueChange = { viewModel.onEvent(RegisterEvent.OnDateOfBirthChanged(it)) },
            onErrorClear = { viewModel.onEvent(RegisterEvent.OnDateOfBirthErrorCleared) },
        )

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        AuthTextField(
            value = state.phone,
            onValueChange = { viewModel.onEvent(RegisterEvent.OnPhoneChanged(it)) },
            placeholder = "Enter phone",
            imeAction = ImeAction.Next,
            errorMessage = state.phoneError,
            onErrorClear = { viewModel.onEvent(RegisterEvent.OnPhoneErrorCleared) },
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        AuthTextField(
            value = state.password,
            placeholder = "Enter password",
            isPassword = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            errorMessage = state.passwordError,
            onValueChange = {
                viewModel.onEvent(RegisterEvent.OnPasswordChanged(it))
            },
            onErrorClear = {
                viewModel.onEvent(RegisterEvent.OnPasswordErrorCleared)
            }
        )

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        PrimaryButton(
            text = "Register",
            onClick = { viewModel.onEvent(RegisterEvent.OnRegisterClick) },
            isLoading = state.isLoading
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account?",
                color = MaterialTheme.colorScheme.onSecondary,
                fontStyle = MaterialTheme.typography.bodySmall.fontStyle
            )

            TextButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("Log In")
            }
        }
    }
}
