import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import kotlinx.coroutines.CoroutineScope
import org.codewithyou365.Database
import org.koin.dsl.module

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual val platformModule = module {
    single<SqlDriver> {
        //JdbcSqliteDriver(Database.Schema, "repeat.db")
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        driver
    }
}


actual abstract class CoroutineViewModel actual constructor() {
    actual val coroutineScope: CoroutineScope
        get() = TODO("Not yet implemented")

    actual fun dispose() {
    }

    protected actual open fun onCleared() {
    }

}