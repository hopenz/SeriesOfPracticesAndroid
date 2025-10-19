package ru.hopenz.pratcticandroid.gp.domain.usecase

import ru.hopenz.pratcticandroid.gp.data.repository.CharacterRepository
import ru.hopenz.pratcticandroid.gp.presentation.model.CharacterUiModel

class GetCharactersUseCase(private val repository: CharacterRepository) {

    suspend operator fun invoke(lang: String = "en"): List<CharacterUiModel> {
        return repository.getCharacters(lang)
    }
}