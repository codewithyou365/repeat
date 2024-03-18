import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.codewithyou365.Database
import platform.UIKit.UIDevice
import org.koin.dsl.module

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()


actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(Database.Schema, "repeat.db") }
}


actual abstract class CoroutineViewModel {
    actual val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    actual fun dispose() {
        coroutineScope.cancel()
        onCleared()
    }

    protected actual open fun onCleared() {
    }
}