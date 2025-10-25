package ru.hopenz.pratcticandroid.di

import androidx.room.Room
import org.koin.dsl.module
import ru.hopenz.pratcticandroid.gp.data.local.AppDatabase

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
    single { get<AppDatabase>().favoriteDao() }
}