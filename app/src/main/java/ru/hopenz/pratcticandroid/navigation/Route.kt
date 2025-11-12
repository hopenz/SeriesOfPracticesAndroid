package ru.hopenz.pratcticandroid.navigation

import androidx.compose.foundation.layout.WindowInsets

sealed interface Route

object Characters : Route
object Favorites : Route
object Settings : Route
data class CharacterDetails(val characterId: Int) : Route

object Profile : Route
object EditProfile : Route