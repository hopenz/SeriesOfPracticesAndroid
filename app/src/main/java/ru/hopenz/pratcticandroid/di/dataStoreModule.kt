package ru.hopenz.pratcticandroid.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.hopenz.pratcticandroid.gp.data.SettingsDataStore

val dataStoreModule = module {
    single { SettingsDataStore(androidContext()) }
}