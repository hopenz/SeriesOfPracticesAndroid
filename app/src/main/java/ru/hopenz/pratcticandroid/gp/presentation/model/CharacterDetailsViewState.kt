package ru.hopenz.pratcticandroid.gp.presentation.model

import ru.hopenz.pratcticandroid.gp.domain.model.CharacterEntity


data class CharacterDetailsViewState(
    val isLoading: Boolean = false,
    val character: CharacterEntity? = null,
    val error: String? = null,
    val rating: Float = 0f,
    val isFavorite: Boolean = false
)