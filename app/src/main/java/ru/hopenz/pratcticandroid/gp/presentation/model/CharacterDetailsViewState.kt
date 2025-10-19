package ru.hopenz.pratcticandroid.gp.presentation.model


data class CharacterDetailsViewState(
    val isLoading: Boolean = false,
    val character: CharacterUiModel? = null,
    val rating: Float = 0f,
    val isFavorite: Boolean = false,
    val error: String? = null
) {
    val userVoteVisible get() = rating != 0f
}