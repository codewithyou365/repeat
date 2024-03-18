package widget.swipe.drawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import widget.base.horizontalDraggable
import kotlin.math.roundToInt

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    state: DrawerState = rememberSwipeState(),
    openLayerWidth: Dp,
    closeLayerWidth: Dp,
    layerWidth: Dp,
    layer: @Composable () -> Unit,
    content: @Composable BoxScope.() -> Unit
) = Box(modifier) {
    val scope = rememberCoroutineScope()
    state.also {
        it.openLayerWidthPx = LocalDensity.current.run { openLayerWidth.toPx() }
        it.closeLayerWidthPx = LocalDensity.current.run { closeLayerWidth.toPx() }
        it.layerWidthPx = LocalDensity.current.run { layerWidth.toPx() }
    }
    Box(
        modifier = Modifier
            .absoluteOffset { IntOffset(x = state.offset.value.roundToInt(), y = 0) }
            .horizontalDraggable(
                enabled = !state.beingFixedOnRelease,
                onDragStopped = {
                    scope.launch {
                        state.handleOnDragStopped()
                    }
                },
                state = state.draggableState,
            ),
        content = content
    )

    if (state.showLayer) {
        Row(
            modifier = Modifier.matchParentSize()
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(width = placeable.width, height = placeable.height) {
                        val iconOffset = constraints.maxWidth + state.offset.value
                        placeable.placeRelative(x = iconOffset.roundToInt(), y = 0)
                    }
                },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            layer()
        }
    }
}