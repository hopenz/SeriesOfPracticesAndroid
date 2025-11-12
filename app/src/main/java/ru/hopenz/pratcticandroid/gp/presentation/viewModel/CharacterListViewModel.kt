package ru.hopenz.pratcticandroid.gp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.hopenz.pratcticandroid.gp.data.SettingsDataStore
import ru.hopenz.pratcticandroid.gp.domain.model.FilterSettings
import ru.hopenz.pratcticandroid.gp.domain.usecase.GetCharactersUseCase
import ru.hopenz.pratcticandroid.gp.presentation.model.CharacterListViewState
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CharacterListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val mutableState = MutableStateFlow(CharacterListViewState())
    val state = mutableState.asStateFlow()

    private val mutableHasSettings = MutableStateFlow(false)
    val hasSettings = mutableHasSettings.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val errorMessage = when (throwable) {
            is UnknownHostException -> "Нет подключения к интернету"
            is SocketTimeoutException -> "Превышено время ожидания ответа"
            is IOException -> "Ошибка сети"
            else -> throwable.message ?: "Ошибка загрузки"
        }
        mutableState.value = CharacterListViewState(CharacterListViewState.State.Error(errorMessage))
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            settingsDataStore.filtersFlow.collect { filters ->
                mutableHasSettings.value = hasNonDefaultSettings(filters)
                loadCharactersInternal(filters)
            }
        }
    }

    fun loadCharacters(lang: String = "en") {
        viewModelScope.launch(exceptionHandler) {
            val filters = settingsDataStore.filtersFlow.first()
            mutableHasSettings.value = hasNonDefaultSettings(filters)
            loadCharactersInternal(filters, lang)
        }
    }

    private suspend fun loadCharactersInternal(filters: FilterSettings, lang: String = "en") {
        mutableState.value = CharacterListViewState(CharacterListViewState.State.Loading)

        val all = getCharactersUseCase(lang)

        val filtered = all.filter { character ->
            val matchesSurname =
                filters.surname.isBlank() ||
                        character.fullName.contains(filters.surname, ignoreCase = true)

            val matchesHouse =
                filters.house.isNullOrBlank() ||
                        (character.hogwartsHouse?.equals(filters.house, ignoreCase = true)
                            ?: false)

            matchesSurname && matchesHouse
        }

        mutableState.value =
            CharacterListViewState(CharacterListViewState.State.Success(filtered))
    }

    private fun hasNonDefaultSettings(filters: FilterSettings): Boolean {
        return filters.surname.isNotBlank() || !filters.house.isNullOrBlank()
    }
}
