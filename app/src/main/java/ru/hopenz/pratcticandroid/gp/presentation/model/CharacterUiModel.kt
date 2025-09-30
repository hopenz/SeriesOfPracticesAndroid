package ru.hopenz.pratcticandroid.gp.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterUiModel(
    val index: Int,
    val fullName: String,
    val nickname: String?,
    val hogwartsHouse: String?,
    val interpretedBy: String?,
    val children: List<String> = emptyList(),
    val imageUrl: String?,
    val birthdate: String?
)
