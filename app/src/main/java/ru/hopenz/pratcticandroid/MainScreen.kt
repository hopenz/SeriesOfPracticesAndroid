package ru.hopenz.pratcticandroid

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.hopenz.pratcticandroid.gp.presentation.screen.CharacterDetailsScreen
import ru.hopenz.pratcticandroid.gp.presentation.screen.CharacterListScreen
import ru.hopenz.pratcticandroid.gp.presentation.screen.FavoritesScreen
import ru.hopenz.pratcticandroid.gp.presentation.screen.SettingsScreen
import ru.hopenz.pratcticandroid.navigation.CharacterDetails
import ru.hopenz.pratcticandroid.navigation.Characters
import ru.hopenz.pratcticandroid.navigation.EditProfile
import ru.hopenz.pratcticandroid.navigation.Favorites
import ru.hopenz.pratcticandroid.navigation.Profile
import ru.hopenz.pratcticandroid.navigation.Route
import ru.hopenz.pratcticandroid.navigation.Settings
import ru.hopenz.pratcticandroid.navigation.TopLevelBackStack
import ru.hopenz.pratcticandroid.profile.presentation.screen.EditProfileScreen
import ru.hopenz.pratcticandroid.profile.presentation.screen.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(topLevelBackStack: TopLevelBackStack<Route>) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                listOf(Characters, Favorites, Profile).forEach { route ->
                    val (icon, label) = when (route) {
                        Characters -> Icons.Default.Home to "Главная"
                        Favorites -> Icons.Default.Favorite to "Избранное"
                        Profile -> Icons.Default.Person to "Профиль"
                        else -> Icons.Default.Home to "Другое"
                    }

                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = topLevelBackStack.topLevelKey == route,
                        onClick = { topLevelBackStack.addTopLevel(route) }
                    )
                }
            }
        }
    ) { padding ->
        when (val currentRoute = topLevelBackStack.backStack.lastOrNull()) {
            is Characters -> {
                CharacterListScreen(topLevelBackStack = topLevelBackStack)
            }
            is Favorites -> {
                FavoritesScreen(topLevelBackStack = topLevelBackStack)
            }
            is Profile -> {
                ProfileScreen(topLevelBackStack = topLevelBackStack)
            }
            is EditProfile -> {
                EditProfileScreen(
                    topLevelBackStack = topLevelBackStack,
                    onBackClick = { topLevelBackStack.removeLast() }
                )
            }
            is Settings -> {
                SettingsScreen(onBackClick = { topLevelBackStack.removeLast() })
            }
            is CharacterDetails -> {
                CharacterDetailsScreen(
                    characterIndex = currentRoute.characterId,
                    onBackClick = { topLevelBackStack.removeLast() }
                )
            }
            else -> {
                CharacterListScreen(topLevelBackStack = topLevelBackStack)
            }
        }
    }
}