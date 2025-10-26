package ru.hopenz.pratcticandroid.gp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.hopenz.pratcticandroid.gp.data.local.FavoriteEntity
import ru.hopenz.pratcticandroid.gp.data.repository.FavoritesRepository

class FavoritesViewModel(private val repository: FavoritesRepository) : ViewModel() {

    val favorites: StateFlow<List<FavoriteEntity>> = repository.favorites
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch {
            repository.addFavorite(favorite)
        }
    }

    fun removeFavorite(id: Int) {
        viewModelScope.launch {
            repository.removeFavorite(id)
        }
    }

    fun isFavoriteFlow(id: Int): StateFlow<Boolean> =
        favorites.map { list -> list.any { it.id == id } }
            .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun isFavorite(id: Int): Boolean = favorites.value.any { it.id == id }
}