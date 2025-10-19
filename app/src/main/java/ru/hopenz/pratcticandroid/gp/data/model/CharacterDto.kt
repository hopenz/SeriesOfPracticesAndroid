package ru.hopenz.pratcticandroid.gp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val fullName: String,
    val nickname: String? = null,
    val hogwartsHouse: String? = null,
    val interpretedBy: String? = null,
    val children: List<String> = emptyList(),
    val image: String? = null,
    val birthdate: String? = null
)