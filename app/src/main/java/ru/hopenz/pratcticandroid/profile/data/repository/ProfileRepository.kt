package ru.hopenz.pratcticandroid.profile.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import ru.hopenz.pratcticandroid.profile.data.model.Profile

private val Context.dataStore by preferencesDataStore("profile_prefs")

class ProfileRepository(private val context: Context) {
    private val ds = context.dataStore

    private object Keys {
        val FULL_NAME = stringPreferencesKey("profile_full_name")
        val AVATAR_URI = stringPreferencesKey("profile_avatar_uri")
        val RESUME_URL = stringPreferencesKey("profile_resume_url")
        val POSITION = stringPreferencesKey("profile_position")
        val FAVORITE_PAIR_TIME = stringPreferencesKey("profile_favorite_pair_time") // новое поле
    }

    val profileFlow: Flow<Profile> = ds.data.map { prefs ->
        Profile(
            fullName = prefs[Keys.FULL_NAME] ?: "",
            avatarUri = prefs[Keys.AVATAR_URI],
            resumeUrl = prefs[Keys.RESUME_URL],
            position = prefs[Keys.POSITION],
            favoritePairTime = prefs[Keys.FAVORITE_PAIR_TIME] // новое поле
        )
    }

    suspend fun saveProfile(profile: Profile) {
        ds.edit { prefs ->
            prefs[Keys.FULL_NAME] = profile.fullName
            prefs[Keys.AVATAR_URI] = profile.avatarUri ?: ""
            prefs[Keys.RESUME_URL] = profile.resumeUrl ?: ""
            prefs[Keys.POSITION] = profile.position ?: ""
            prefs[Keys.FAVORITE_PAIR_TIME] = profile.favoritePairTime ?: "" // сохранение нового поля
        }
    }

    suspend fun updateAvatar(uriString: String?) {
        ds.edit { prefs ->
            if (uriString == null) prefs.remove(Keys.AVATAR_URI)
            else prefs[Keys.AVATAR_URI] = uriString
        }
    }
}