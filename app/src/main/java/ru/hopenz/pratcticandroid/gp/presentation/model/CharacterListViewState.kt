package ru.hopenz.pratcticandroid.gp.presentation.model

data class CharacterListViewState(
    val state: State = State.Loading
) {
    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State
        data class Success(val data: List<CharacterUiModel>) : State
    }
}