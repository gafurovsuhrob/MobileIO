package com.seros.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BaseAlertDialog(
    title: String,
    text: String,
    confirmButtonText: String = "ОК",
    dismissButtonText: String? = null,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: (() -> Unit)? = null,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = Color.White,
        title = { Text(title, style = MaterialTheme.typography.titleLarge) },
        text = { Text(text) },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {
            if (dismissButtonText != null){
                TextButton(onClick = {
                    onDismiss?.invoke() ?: onDismissRequest()
                }) {
                    Text(dismissButtonText)
                }
            }
        }
    )
}