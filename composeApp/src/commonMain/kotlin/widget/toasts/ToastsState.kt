package widget.toasts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource

@Composable
fun rememberToastsState(): ToastsState {
    val scope: CoroutineScope = rememberCoroutineScope()
    return remember { ToastsState(scope) }
}

@OptIn(ExperimentalResourceApi::class)
@Stable
class ToastsState internal constructor(private val scope: CoroutineScope) {
    internal val contents = mutableStateListOf<StringResource>()
    internal val count = mutableIntStateOf(0)
    fun show(content: StringResource) {
        val that = this
        this.scope.launch {
            delay(1000)
            --that.count.value
            delay(1000)
            if (that.count.value == 0) {
                that.contents.clear()
            }
        }
        ++this.count.value
        this.contents.add(content)
    }

}