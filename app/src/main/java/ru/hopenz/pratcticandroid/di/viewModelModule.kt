package ru.hopenz.pratcticandroid.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.SettingsViewModel

val viewModelModule = module {
    viewModel { SettingsViewModel(get()) }
}