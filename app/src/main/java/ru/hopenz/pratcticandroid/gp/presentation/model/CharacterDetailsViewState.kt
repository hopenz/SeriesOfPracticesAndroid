package ru.hopenz.pratcticandroid.gp.presentation.model

data class CharacterDetailsViewState(
    val character: CharacterUiModel,
    val rating: Float = 0f,
    val isFavorite: Boolean = false
) {
    val userVoteVisible get() = rating != 0f
}