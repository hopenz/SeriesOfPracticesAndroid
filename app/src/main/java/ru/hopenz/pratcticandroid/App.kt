package ru.hopenz.pratcticandroid

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.hopenz.pratcticandroid.di.dataStoreModule
import ru.hopenz.pratcticandroid.di.databaseModule
import ru.hopenz.pratcticandroid.di.mainModule
import ru.hopenz.pratcticandroid.di.networkModules
import ru.hopenz.pratcticandroid.di.potterFeatureModule
import ru.hopenz.pratcticandroid.di.viewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                mainModule,
                networkModules,
                potterFeatureModule,
                dataStoreModule,
                viewModelModule,
                databaseModule
            )
        }
    }
}