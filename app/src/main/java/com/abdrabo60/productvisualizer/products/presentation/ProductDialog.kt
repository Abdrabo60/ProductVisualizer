package com.abdrabo60.productvisualizer.products.presentation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.DialogProperties

@Composable
fun ProductDialog(
    product: UIProduct? = null, onDismiss: () -> Unit, onConfirm: (product: UIProduct) -> Unit
) {
    val p = remember {
        mutableStateOf(product ?: UIProduct("", ""))
    }
    AlertDialog(properties = DialogProperties(dismissOnClickOutside = false),
        title = { Text(text = "Product Name") },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onConfirm(p.value) }) {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        },
        text = {
            TextField(singleLine = true,
                value = p.value.name,
                onValueChange = { p.value = p.value.copy(name = it) })

        })
}