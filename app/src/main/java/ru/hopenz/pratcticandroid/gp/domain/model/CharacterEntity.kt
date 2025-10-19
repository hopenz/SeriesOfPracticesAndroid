package ru.hopenz.pratcticandroid.gp.domain.model

data class CharacterEntity(
    val id: Int,
    val fullName: String,
    val nickname: String? = null,
    val hogwartsHouse: String? = null,
    val interpretedBy: String? = null,
    val children: List<String> = emptyList(),
    val imageUrl: String? = null,
    val birthdate: String? = null
)