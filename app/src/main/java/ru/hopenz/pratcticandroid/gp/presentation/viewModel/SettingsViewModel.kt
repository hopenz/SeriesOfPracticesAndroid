package ru.hopenz.pratcticandroid.gp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.hopenz.pratcticandroid.gp.data.SettingsDataStore
import ru.hopenz.pratcticandroid.gp.domain.model.FilterSettings

class SettingsViewModel(
    private val dataStore: SettingsDataStore
) : ViewModel() {

    private val _filters = MutableStateFlow(FilterSettings())
    val filters: StateFlow<FilterSettings> = _filters

    val faculties = listOf("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff")

    init {
        viewModelScope.launch {
            dataStore.filtersFlow.collectLatest { settings ->
                _filters.value = settings
            }
        }
    }

    fun selectFaculty(faculty: String) {
        _filters.update { it.copy(house = faculty) }
        saveFilters(_filters.value)
    }

    fun saveFilters(newFilters: FilterSettings) {
        viewModelScope.launch {
            dataStore.updateFilters(newFilters)
        }
    }
}