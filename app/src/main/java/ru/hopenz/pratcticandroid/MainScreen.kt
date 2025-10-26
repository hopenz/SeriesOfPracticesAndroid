package ru.hopenz.pratcticandroid

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import ru.hopenz.pratcticandroid.navigation.Favorites
import ru.hopenz.pratcticandroid.navigation.Route
import ru.hopenz.pratcticandroid.navigation.Settings
import ru.hopenz.pratcticandroid.navigation.TopLevelBackStack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(topLevelBackStack: TopLevelBackStack<Route>) {
    val dialogStrategy = remember { DialogSceneStrategy<Route>() }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                listOf(Characters, Favorites).forEach { route ->
                    NavigationBarItem(
                        icon = {},
                        label = { Text(route::class.simpleName ?: "") },
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
            modifier = Modifier.padding(padding),
            sceneStrategy = dialogStrategy,
            entryProvider = entryProvider {
                entry<Characters> {
                    val viewModel: CharacterListViewModel = koinViewModel()
                    CharacterListScreen(
                        topLevelBackStack = topLevelBackStack,
                        viewModel = viewModel
                    )
                }
                entry<CharacterDetails> {
                    val route = it as CharacterDetails
                    val viewModel: CharacterDetailsViewModel =
                        koinViewModel { parametersOf(route.characterId) }
                    CharacterDetailsScreen(
                        characterIndex = route.characterId,
                        onBackClick = { topLevelBackStack.removeLast() }
                    )
                }
                entry<Favorites> {
                    val favoritesViewModel: FavoritesViewModel = koinViewModel()
                    FavoritesScreen(
                        topLevelBackStack = topLevelBackStack,
                        viewModel = favoritesViewModel
                    )
                }
                entry<Settings> {
                    val viewModel: SettingsViewModel = koinViewModel()
                    SettingsScreen(
                        onBackClick = { topLevelBackStack.removeLast() },
                        viewModel = viewModel
                    )
                }
            }
        )
    }
}
