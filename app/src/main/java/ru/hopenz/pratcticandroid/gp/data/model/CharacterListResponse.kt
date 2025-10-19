package ru.hopenz.pratcticandroid.gp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterListResponse(
    val characters: List<CharacterDto>
)