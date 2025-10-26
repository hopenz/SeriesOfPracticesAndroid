package ru.hopenz.pratcticandroid.gp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.hopenz.pratcticandroid.gp.domain.model.FilterSettings

private val Context.dataStore by preferencesDataStore("filter_prefs")

class SettingsDataStore(context: Context) {

    private val ds = context.dataStore

    private val SURNAME_KEY = stringPreferencesKey("surname")
    private val HOUSE_KEY = stringPreferencesKey("house")

    val filtersFlow: Flow<FilterSettings> = ds.data
        .catch { emit(emptyPreferences()) }
        .map { prefs ->
            FilterSettings(
                surname = prefs[SURNAME_KEY] ?: "",
                house = prefs[HOUSE_KEY]
            )
        }

    suspend fun updateFilters(filters: FilterSettings) {
        ds.edit { prefs ->
            prefs[SURNAME_KEY] = filters.surname
            if (filters.house != null) prefs[HOUSE_KEY] = filters.house
            else prefs.remove(HOUSE_KEY)
        }
    }
}
