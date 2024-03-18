package widget.toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import repeat.composeapp.generated.resources.Res
import repeat.composeapp.generated.resources.none

@Composable
fun rememberToastState(): ToastState {
    val scope: CoroutineScope = rememberCoroutineScope()
    return remember { ToastState(scope) }
}

@OptIn(ExperimentalResourceApi::class)
@Stable
class ToastState internal constructor(private val scope: CoroutineScope) {
    internal val content = mutableStateOf(Res.string.none)
    internal val count = mutableIntStateOf(0)
    internal val show = mutableStateOf(false)
    fun show(content: StringResource) {
        val that = this
        this.scope.launch {
            delay(1000)
            --that.count.value
            delay(1000)
            if (that.count.value == 0) {
                that.show.value = false
            }
        }
        ++this.count.value
        this.show.value = true
        this.content.value = content
    }

}