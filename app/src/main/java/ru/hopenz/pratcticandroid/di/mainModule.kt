package ru.hopenz.pratcticandroid.di

import org.koin.dsl.module
import ru.hopenz.pratcticandroid.Characters
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.CharacterDetailsViewModel
import ru.hopenz.pratcticandroid.navigation.Route
import ru.hopenz.pratcticandroid.navigation.TopLevelBackStack

val mainModule = module {
    single { TopLevelBackStack<Route>(Characters) }

    factory { (characterId: Int) ->
        CharacterDetailsViewModel(get<TopLevelBackStack<Route>>(), characterId)
    }
}

