package ru.hopenz.pratcticandroid.gp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.hopenz.pratcticandroid.gp.data.api.CharacterApi
import ru.hopenz.pratcticandroid.gp.data.mapper.CharacterResponseToUiMapper
import ru.hopenz.pratcticandroid.gp.presentation.model.CharacterUiModel

class CharacterRepository(
    private val api: CharacterApi,
    private val mapper: CharacterResponseToUiMapper
) {
    suspend fun getCharacters(lang: String = "en"): List<CharacterUiModel> =
        withContext(Dispatchers.IO) {
            val response = api.getCharacters(lang)
            mapper.mapResponse(response)
        }
}
