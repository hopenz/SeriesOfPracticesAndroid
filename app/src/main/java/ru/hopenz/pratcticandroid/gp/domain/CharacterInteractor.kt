package ru.hopenz.pratcticandroid.gp.domain

import ru.hopenz.pratcticandroid.gp.data.repository.CharacterRepository

class CharacterInteractor(
    private val repository: CharacterRepository
) {
    suspend fun getCharacters(lang: String = "en") = repository.getCharacters(lang)
}