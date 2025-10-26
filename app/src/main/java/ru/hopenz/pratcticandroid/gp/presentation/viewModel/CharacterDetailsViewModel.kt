package ru.hopenz.pratcticandroid.gp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.hopenz.pratcticandroid.gp.data.repository.CharacterRepository
import ru.hopenz.pratcticandroid.gp.domain.model.CharacterEntity
import ru.hopenz.pratcticandroid.gp.presentation.model.CharacterDetailsViewState
import ru.hopenz.pratcticandroid.navigation.Route
import ru.hopenz.pratcticandroid.navigation.TopLevelBackStack
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CharacterDetailsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val repository: CharacterRepository,
    private val characterIndex: Int
) : ViewModel() {

    private val mutableState = MutableStateFlow(
        CharacterDetailsViewState(isLoading = true)
    )
    val state = mutableState.asStateFlow()

    init {
        loadCharacter(characterIndex)
    }

    fun loadCharacter(characterIndex: Int) {
        viewModelScope.launch {
            mutableState.update { it.copy(isLoading = true, error = null) }

            try {
                val charactersDto = repository.getCharacters()
                val characterEntity =
                    charactersDto.firstOrNull { it.index == characterIndex }?.let { dto ->
                        CharacterEntity(
                            id = dto.index,
                            fullName = dto.fullName,
                            nickname = dto.nickname,
                            hogwartsHouse = dto.hogwartsHouse,
                            interpretedBy = dto.interpretedBy,
                            children = dto.children,
                            imageUrl = dto.imageUrl,
                            birthdate = dto.birthdate
                        )
                    }

                if (characterEntity != null) {
                    mutableState.update { it.copy(isLoading = false, character = characterEntity) }
                } else {
                    mutableState.update { it.copy(isLoading = false, error = "Персонаж не найден") }
                }

            } catch (e: UnknownHostException) {
                mutableState.update {
                    it.copy(
                        isLoading = false,
                        error = "Нет подключения к интернету"
                    )
                }
            } catch (e: SocketTimeoutException) {
                mutableState.update {
                    it.copy(
                        isLoading = false,
                        error = "Превышено время ожидания ответа"
                    )
                }
            } catch (e: IOException) {
                mutableState.update { it.copy(isLoading = false, error = "Ошибка сети") }
            } catch (e: Exception) {
                mutableState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Ошибка загрузки персонажа"
                    )
                }
            }
        }
    }

    fun onRatingChanged(rating: Float) {
        mutableState.update { it.copy(rating = rating) }
    }

    fun onFavoriteToggled() {
        mutableState.update { it.copy(isFavorite = !it.isFavorite) }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}