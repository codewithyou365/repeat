import dao.ConfigGitDao
import org.codewithyou365.Database
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        platformModule,
        databaseModule,
        viewModelModule
    )
}

private val databaseModule = module {
    single { Database(get()) }
}


private val viewModelModule = module {
    factory { ConfigGitDao(get()) }
}


