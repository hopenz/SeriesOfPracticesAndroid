package ru.hopenz.pratcticandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.hopenz.pratcticandroid.gp.presentation.screen.CharacterDetailsScreen
import ru.hopenz.pratcticandroid.gp.presentation.screen.CharacterListScreen
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.CharacterDetailsViewModel
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.CharacterListViewModel
import ru.hopenz.pratcticandroid.navigation.Route
import ru.hopenz.pratcticandroid.navigation.TopLevelBackStack

data object Characters : Route
data object Faculties : Route
data class CharacterDetails(val characterId: Int) : Route

@Composable
fun MainScreen(topLevelBackStack: TopLevelBackStack<Route>) {
    val dialogStrategy = remember { DialogSceneStrategy<Route>() }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                listOf(Characters, Faculties).forEach { route ->
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
                entry<Faculties> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Faculties screen")
                    }
                }
            }
        )
    }
}