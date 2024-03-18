import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.Module

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

//@Suppress("NO_ACTUAL_FOR_EXPECT")
expect val platformModule: Module


expect abstract class CoroutineViewModel() {
    val coroutineScope: CoroutineScope

    fun dispose()

    protected open fun onCleared()
}