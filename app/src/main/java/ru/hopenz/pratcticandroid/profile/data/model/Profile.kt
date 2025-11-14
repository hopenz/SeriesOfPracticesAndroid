package ru.hopenz.pratcticandroid.profile.data.model

data class Profile(
    val fullName: String = "",
    val avatarUri: String? = null,
    val resumeUrl: String? = null,
    val position: String? = null,
    val favoritePairTime: String? = null
)