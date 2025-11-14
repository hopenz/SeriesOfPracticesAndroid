package ru.hopenz.pratcticandroid.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.SettingsViewModel
import ru.hopenz.pratcticandroid.profile.presentation.viewModel.ProfileViewModel

val viewModelModule = module {
    viewModel { SettingsViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
}