package ru.hopenz.pratcticandroid.profile.presentation.viewModel

import android.app.Application
import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.hopenz.pratcticandroid.profile.data.model.Profile
import ru.hopenz.pratcticandroid.profile.data.repository.ProfileRepository

class ProfileViewModel(
    application: Application,
    private val repo: ProfileRepository
) : AndroidViewModel(application) {

    val profile: StateFlow<Profile> = repo.profileFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, Profile())

    fun saveProfile(profile: Profile) {
        viewModelScope.launch { repo.saveProfile(profile) }
    }

    fun setAvatar(uriString: String?) {
        viewModelScope.launch { repo.updateAvatar(uriString) }
    }

    fun setFavoritePairTime(time: String?) {
        viewModelScope.launch {
            val currentProfile = profile.value
            repo.saveProfile(currentProfile.copy(favoritePairTime = time))
        }
    }

    fun openResume(resumeUrl: String?, onError: (String) -> Unit, openIntent: (Intent) -> Unit) {
        if (resumeUrl.isNullOrBlank()) {
            onError("Ссылка на резюме не указана")
            return
        }

        try {
            val uri = resumeUrl.toUri()
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = uri
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            openIntent(intent)
        } catch (t: Throwable) {
            onError("Не удалось открыть резюме: ${t.message}")
        }
    }
}