package ru.hopenz.pratcticandroid.gp.presentation.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.hopenz.pratcticandroid.gp.presentation.MockData
import ru.hopenz.pratcticandroid.gp.presentation.model.CharacterDetailsViewState
import ru.hopenz.pratcticandroid.navigation.Route
import ru.hopenz.pratcticandroid.navigation.TopLevelBackStack

class CharacterDetailsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    characterIndex: Int
) : ViewModel() {

    private val character = MockData.getCharacters().first { it.index == characterIndex }

    private val mutableState = MutableStateFlow(CharacterDetailsViewState(character))
    val state = mutableState.asStateFlow()

    fun onRatingChanged(rating: Float) {
        mutableState.update { it.copy(rating = rating) }
    }

    fun onFavoriteToggled() {
        mutableState.update { it.copy(isFavorite = !it.isFavorite) }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}
