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
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.hopenz.pratcticandroid.gp.presentation.screen.CharacterDetailsScreen
import ru.hopenz.pratcticandroid.gp.presentation.screen.CharacterListScreen
import ru.hopenz.pratcticandroid.gp.presentation.screen.FavoritesScreen
import ru.hopenz.pratcticandroid.gp.presentation.screen.SettingsScreen
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.CharacterDetailsViewModel
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.CharacterListViewModel
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.FavoritesViewModel
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.SettingsViewModel
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
import kotlin.collections.forEach

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(topLevelBackStack: TopLevelBackStack<Route>) {
    val dialogStrategy = remember { DialogSceneStrategy<Route>() }

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
        NavDisplay(
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            modifier = Modifier
                .padding(padding)
                .background(Color.White),
            sceneStrategy = dialogStrategy,
            entryProvider = entryProvider {

                entry<Characters> {
                    CharacterListScreen(topLevelBackStack = topLevelBackStack)
                }

                entry<Favorites> {
                    FavoritesScreen(topLevelBackStack = topLevelBackStack)
                }

                entry<Profile> {
                    ProfileScreen(topLevelBackStack = topLevelBackStack)
                }

                entry<EditProfile> {
                    EditProfileScreen(
                        topLevelBackStack = topLevelBackStack,
                        onBackClick = { topLevelBackStack.removeLast() }
                    )
                }

                entry<Settings> {
                    SettingsScreen(onBackClick = { topLevelBackStack.removeLast() })
                }

                entry<CharacterDetails> {
                    val route = it as CharacterDetails
                    CharacterDetailsScreen(
                        characterIndex = route.characterId,
                        onBackClick = { topLevelBackStack.removeLast() }
                    )
                }
            }
        )
    }
}
