package com.denior.parasol.ui.citySearch

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LocationCityDialog(
    addCityToDatabase: () -> Unit,
    addCityButtonEnabling: Boolean,
    onDismissRequest: () -> Unit,
    alertText: @Composable (() -> Unit)?
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(enabled = addCityButtonEnabling, onClick = addCityToDatabase) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Text(text = "Add city")
            }
        },
        text = alertText
    )
}