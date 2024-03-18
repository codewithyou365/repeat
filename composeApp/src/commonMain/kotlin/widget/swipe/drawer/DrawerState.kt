package widget.swipe.drawer

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberSwipeState(): DrawerState {
    return remember { DrawerState() }
}

enum class AutoSwipeState { CLOSE_FINISH, OPEN_FINISH, OPEN_PROCEED, CLOSE_PROCEED }

@Stable
class DrawerState internal constructor() {

    val offset: State<Float> get() = offsetState
    private var offsetState = mutableStateOf(0f)


    private var autoSwipeState: AutoSwipeState by mutableStateOf(AutoSwipeState.CLOSE_FINISH)
    internal var openLayerWidthPx: Float by mutableFloatStateOf(0f)
    internal var closeLayerWidthPx: Float by mutableFloatStateOf(0f)
    internal var layerWidthPx: Float by mutableFloatStateOf(0f)

    internal val draggableState = DraggableState { delta ->
        offsetState.value += delta
    }

    private val autoMovementDistance: Float by derivedStateOf {
        if (autoSwipeState == AutoSwipeState.OPEN_PROCEED) {
            layerWidthPx + offsetState.value
        } else if (autoSwipeState == AutoSwipeState.CLOSE_PROCEED) {
            offsetState.value
        } else if (autoSwipeState == AutoSwipeState.CLOSE_FINISH) {
            if (openLayerWidthPx + offsetState.value < 0) {
                autoSwipeState = AutoSwipeState.OPEN_PROCEED
                layerWidthPx + offsetState.value
            } else {
                autoSwipeState = AutoSwipeState.CLOSE_PROCEED
                offsetState.value
            }
        } else if (autoSwipeState == AutoSwipeState.OPEN_FINISH) {
            if (layerWidthPx - closeLayerWidthPx + offsetState.value > 0) {
                autoSwipeState = AutoSwipeState.CLOSE_PROCEED
                offsetState.value

            } else {
                autoSwipeState = AutoSwipeState.OPEN_PROCEED
                layerWidthPx + offsetState.value
            }
        } else {
            autoSwipeState = AutoSwipeState.CLOSE_PROCEED
            offsetState.value
        }
    }

    internal val beingFixedOnRelease: Boolean by derivedStateOf {
        autoSwipeState == AutoSwipeState.OPEN_PROCEED || autoSwipeState == AutoSwipeState.CLOSE_PROCEED
    }
    internal val showLayer: Boolean by derivedStateOf {
        offsetState.value < 0
    }

    internal suspend fun handleOnDragStopped() = coroutineScope {
        launch {
            draggableState.drag(MutatePriority.PreventUserInput) {
                Animatable(autoMovementDistance).animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 300),
                ) {
                    dragBy(value - autoMovementDistance)
                }
            }
            if (offsetState.value == 0f) {
                autoSwipeState = AutoSwipeState.CLOSE_FINISH
            } else {
                autoSwipeState = AutoSwipeState.OPEN_FINISH
            }
        }
    }
}