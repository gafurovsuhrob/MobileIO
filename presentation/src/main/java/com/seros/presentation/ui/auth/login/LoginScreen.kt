package com.seros.presentation.ui.auth.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.seros.presentation.R
import com.seros.presentation.components.*
import com.seros.presentation.navigation.Destinations
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.uiState.collectAsState()
    var checked by remember { mutableStateOf(false) }

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            navController.navigate(Destinations.Main) {
                popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(72.dp)
                .padding(bottom = 24.dp)
        )

        ScreenHeader(
            title = "Sign in to your Account",
            subtitle = "Enter your email and password to log in "
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = state.username,
            placeholder = "Enter username",
            imeAction = ImeAction.Next,
            errorMessage = state.usernameError,
            onValueChange = {
                viewModel.onEvent(LoginEvent.OnUsernameChanged(it))
            },
            onErrorClear = {
                viewModel.onEvent(LoginEvent.OnUsernameErrorCleared)
            }
        )

        Spacer(modifier = Modifier.height(6.dp))

        AuthTextField(
            value = state.password,
            placeholder = "Enter password",
            isPassword = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            errorMessage = state.passwordError,
            onValueChange = {
                viewModel.onEvent(LoginEvent.OnPasswordChanged(it))
            },
            onErrorClear = {
                viewModel.onEvent(LoginEvent.OnPasswordErrorCleared)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { checked = !checked }
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.onSecondary,
                        checkmarkColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        disabledCheckedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                    )
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = "Remember me",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontStyle = MaterialTheme.typography.bodySmall.fontStyle
                )
            }

            TextButton(
                onClick = {
                    navController.navigate(Destinations.RecoverPassword)
                }
            ) {
                Text("Forgot Password ?")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            text = "Log In",
            onClick = { viewModel.onEvent(LoginEvent.OnLoginClick) },
            isLoading = state.isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
            Text(
                text = "Or",
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.onSecondary
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        SocialLoginButton(text = "Continue with Google",
            iconResId = R.drawable.ic_google,
            onClick = {
                Toast.makeText(
                    navController.context,
                    "Coming soon",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        SocialLoginButton(text = "Continue with Github",
            iconResId = R.drawable.ic_github,
            onClick = {
                Toast.makeText(
                    navController.context,
                    "Coming soon",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        Spacer(modifier = Modifier.height(32.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don’t have an account?",
                color = MaterialTheme.colorScheme.onSecondary,
                fontStyle = MaterialTheme.typography.bodySmall.fontStyle
            )

            TextButton(
                onClick = {
                    navController.navigate(Destinations.Register)
                }
            ) {
                Text("Sign Up")
            }
        }
    }
}
