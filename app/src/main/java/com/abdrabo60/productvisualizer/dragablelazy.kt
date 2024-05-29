import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun DraggableLazyColumn() {
    var items by remember { mutableStateOf(List(10) { "Item $it" }) }
    var draggedItemIndex by remember { mutableStateOf<Int?>(null) }
    var draggedItemOffset by remember { mutableStateOf(0f) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LazyColumn(state = listState) {
        itemsIndexed(items) { index, item ->
            val elevation by animateDpAsState(if (index == draggedItemIndex) 8.dp else 0.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .offset { IntOffset(x = 0, y = if (index == draggedItemIndex) draggedItemOffset.toInt() else 0) }
                    .background(if (index == draggedItemIndex) Color.LightGray else Color.Gray)
                    .padding(8.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                draggedItemIndex = index
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                draggedItemOffset += dragAmount.y

                                // Check if we should scroll
                                scope.launch {
                                    val layoutInfo = listState.layoutInfo
                                    val visibleItemsInfo = layoutInfo.visibleItemsInfo
                                    if (visibleItemsInfo.isNotEmpty()) {
                                        val firstVisibleItem = visibleItemsInfo.first()
                                        val lastVisibleItem = visibleItemsInfo.last()

                                        when {
                                            draggedItemOffset < 0 && firstVisibleItem.index > 0 -> {
                                                listState.scrollBy(draggedItemOffset)
                                            }
                                            draggedItemOffset > 0 && lastVisibleItem.index < items.size - 1 -> {
                                                listState.scrollBy(draggedItemOffset)
                                            }
                                        }
                                    }
                                }

                                // Check if we should swap items
                                val targetIndex = (draggedItemIndex!! + (draggedItemOffset / 50).toInt()).coerceIn(0, items.size - 1)
                                if (targetIndex != draggedItemIndex) {
                                    val newItems = items.toMutableList()
                                    newItems.add(targetIndex, newItems.removeAt(draggedItemIndex!!))
                                    items = newItems
                                    draggedItemIndex = targetIndex
                                    draggedItemOffset = 0f
                                }
                            },
                            onDragEnd = {
                                draggedItemOffset = 0f
                                draggedItemIndex = null
                            }
                        )
                    }
            ) {
                Text(text = item, modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun MyApp() {
    MaterialTheme {
        Surface {
            DraggableLazyColumn()
        }
    }
}