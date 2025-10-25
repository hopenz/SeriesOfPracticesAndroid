package ru.hopenz.pratcticandroid.navigation

sealed interface Route

object Characters : Route
object Favorites : Route
object Settings : Route
data class CharacterDetails(val characterId: Int) : Route