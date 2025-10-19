package ru.hopenz.pratcticandroid.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.hopenz.pratcticandroid.gp.data.api.CharacterApi

val networkModules = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://potterapi-fedeperin.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(CharacterApi::class.java) }
}