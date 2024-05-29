package com.abdrabo60.productvisualizer.products.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductItem(index: Int, product: UIProduct, onClick: (index: Int) -> Unit) {
    val icon = if (product.selected) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            modifier = Modifier
                .fillMaxWidth(.75f)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = product.name, fontSize = 24.sp, modifier = Modifier.weight(.8f))
                Spacer(modifier = Modifier.weight(.1f))
                IconButton(onClick = { onClick(index) }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "checked",
                        Modifier.weight(.1f)
                    )
                }
            }
        }
    }
}