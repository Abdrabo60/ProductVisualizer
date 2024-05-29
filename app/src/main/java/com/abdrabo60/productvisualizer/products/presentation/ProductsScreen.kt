package com.abdrabo60.productvisualizer.products.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

enum class DialogState() {
    Add, Edit, Hidden

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    state: ProductsState,
    onBackButton: () -> Unit,
    onAddNewProduct: (uiProduct: UIProduct) -> Unit,
    onToggleSelection: (index: Int) -> Unit,
    onClearAllSelection: () -> Unit,
    onDeleteProduct: () -> Unit,
    onEditProduct: (uiProduct: UIProduct) -> Unit
) {
    val editorDialogState = remember {
        mutableStateOf(DialogState.Hidden)
    }
    val deleteAlertState = remember {
        mutableStateOf(false)
    }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val selectedCount = state.products.count { it.selected }
    var isAddingNewItem by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(state.products.size) {
        if (state.products.isNotEmpty() && isAddingNewItem) {
            coroutineScope.launch {
                listState.animateScrollToItem(state.products.size - 1)
                isAddingNewItem=false
            }
        }
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Products") }, navigationIcon = {
            IconButton(onClick = { onBackButton() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "backIcon")
            }
        }, actions = {
            if (selectedCount > 0) {
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    IconButton(onClick = { onClearAllSelection() }) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = "clear selection"
                        )
                    }
                    IconButton(onClick = {
                        if (state.isLoading) return@IconButton;deleteAlertState.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete, contentDescription = "delete"
                        )
                    }
                    if (selectedCount == 1) {
                        IconButton(onClick = {
                            if (state.isLoading) return@IconButton;editorDialogState.value =
                            DialogState.Edit
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Edit, contentDescription = "edit"
                            )
                        }
                    }
                }

            }

        })

    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                if (state.isLoading) return@FloatingActionButton;onClearAllSelection();editorDialogState.value =
                DialogState.Add
            },
            Modifier
                .wrapContentSize()
                .padding(16.dp)
        ) {
            Icon(
                painter = rememberVectorPainter(image = Icons.Filled.Add),
                contentDescription = "Add new product"
            )

        }
    }, floatingActionButtonPosition = FabPosition.End) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                itemsIndexed(state.products, key = { _, item: UIProduct ->
                    item.hashCode()
                }) { index, item ->
                    ProductItem(index, item, onClick = { onToggleSelection(it) })
                }
            }
            if (state.isLoading) CircularProgressIndicator(Modifier.align(Alignment.Center))
            state.error?.let { Text(it) }

        }
    }
    when (editorDialogState.value) {
        DialogState.Edit -> {

            val item = state.products.first { it.selected }
            ProductDialog(item.copy(id = item.id, name = item.name),
                onDismiss = { editorDialogState.value = DialogState.Hidden },
                onConfirm = {
                    onEditProduct(it)
                    editorDialogState.value = DialogState.Hidden
                })
        }

        DialogState.Add -> {
            ProductDialog(onDismiss = { editorDialogState.value = DialogState.Hidden },
                onConfirm = {
                    isAddingNewItem=true
                    onAddNewProduct(it)
                    editorDialogState.value = DialogState.Hidden
                })
        }

        DialogState.Hidden -> {}
    }
    if (deleteAlertState.value) {
        AlertDialog(
            onDismissRequest = { deleteAlertState.value = false },
            confirmButton = {
                Button(onClick = { deleteAlertState.value = false;onDeleteProduct() }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(onClick = { deleteAlertState.value = false }) {
                    Text("No")
                }
            },
            title = { Text(text = "Deleting Products", fontSize = 16.sp) },
            text = { Text(text = "Are you sure to delete selected products?") },
            icon = { Icon(Icons.Filled.Delete, contentDescription = "delete products alert") },

            )
    }
}


@Serializable
object ProductsScreenObject