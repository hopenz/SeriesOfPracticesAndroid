package ru.hopenz.pratcticandroid.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.hopenz.pratcticandroid.gp.data.mapper.CharacterResponseToUiMapper
import ru.hopenz.pratcticandroid.gp.data.repository.CharacterRepository
import ru.hopenz.pratcticandroid.gp.data.repository.FavoritesRepository
import ru.hopenz.pratcticandroid.gp.domain.usecase.GetCharactersUseCase
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.CharacterDetailsViewModel
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.CharacterListViewModel
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.FavoritesViewModel


val potterFeatureModule = module {
    single { CharacterResponseToUiMapper() }
    single { CharacterRepository(get(), get()) }
    single { GetCharactersUseCase(get()) }

    single { FavoritesRepository(get()) }

    viewModel { FavoritesViewModel(get()) }

    viewModel { CharacterListViewModel(get(), settingsDataStore = get()) }

    viewModel { (characterIndex: Int) ->
        CharacterDetailsViewModel(
            topLevelBackStack = get(),
            repository = get(),
            characterIndex = characterIndex
        )
    }
}