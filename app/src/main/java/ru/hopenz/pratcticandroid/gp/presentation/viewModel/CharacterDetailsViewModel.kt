package ru.hopenz.pratcticandroid.gp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
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

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val errorMessage = when (throwable) {
            is UnknownHostException -> "Нет подключения к интернету"
            is SocketTimeoutException -> "Превышено время ожидания ответа"
            is IOException -> "Ошибка сети"
            else -> throwable.message ?: "Ошибка загрузки персонажа"
        }
        mutableState.update { it.copy(isLoading = false, error = errorMessage) }
    }

    init {
        loadCharacter(characterIndex)
    }

    fun loadCharacter(characterIndex: Int) {
        viewModelScope.launch(exceptionHandler) {
            mutableState.update { it.copy(isLoading = true, error = null) }

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