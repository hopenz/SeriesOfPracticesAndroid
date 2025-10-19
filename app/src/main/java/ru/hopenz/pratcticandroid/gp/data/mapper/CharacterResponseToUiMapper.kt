package ru.hopenz.pratcticandroid.gp.data.mapper

import ru.hopenz.pratcticandroid.gp.data.model.CharacterDto
import ru.hopenz.pratcticandroid.gp.presentation.model.CharacterUiModel

class CharacterResponseToUiMapper {
    fun mapResponse(response: List<CharacterDto>): List<CharacterUiModel> {
        return response.mapIndexed { index, character ->
            CharacterUiModel(
                index = index,
                fullName = character.fullName,
                nickname = character.nickname,
                hogwartsHouse = character.hogwartsHouse,
                interpretedBy = character.interpretedBy,
                children = character.children,
                imageUrl = character.image,
                birthdate = character.birthdate
            )
        }
    }
}