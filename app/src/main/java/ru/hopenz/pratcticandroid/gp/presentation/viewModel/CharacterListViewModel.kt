package ru.hopenz.pratcticandroid.gp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.hopenz.pratcticandroid.gp.domain.usecase.GetCharactersUseCase
import ru.hopenz.pratcticandroid.gp.presentation.model.CharacterListViewState
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CharacterListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val mutableState = MutableStateFlow(CharacterListViewState())
    val state = mutableState.asStateFlow()

    fun loadCharacters(lang: String = "en") {
        mutableState.value = CharacterListViewState(CharacterListViewState.State.Loading)

        viewModelScope.launch {
            try {
                val characters = getCharactersUseCase(lang)
                mutableState.value =
                    CharacterListViewState(CharacterListViewState.State.Success(characters))
            } catch (e: UnknownHostException) {
                mutableState.value = CharacterListViewState(
                    CharacterListViewState.State.Error("Нет подключения к интернету")
                )
            } catch (e: SocketTimeoutException) {
                mutableState.value = CharacterListViewState(
                    CharacterListViewState.State.Error("Превышено время ожидания ответа")
                )
            } catch (e: IOException) {
                mutableState.value = CharacterListViewState(
                    CharacterListViewState.State.Error("Ошибка сети")
                )
            } catch (e: Exception) {
                mutableState.value = CharacterListViewState(
                    CharacterListViewState.State.Error(e.message ?: "Ошибка загрузки")
                )
            }
        }
    }
}
