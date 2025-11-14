package ru.hopenz.pratcticandroid.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.hopenz.pratcticandroid.profile.data.repository.ProfileRepository
import ru.hopenz.pratcticandroid.profile.presentation.viewModel.ProfileViewModel

val profileModule = module {
    single { ProfileRepository(get()) }
    viewModel { ProfileViewModel(get(), get()) }
}