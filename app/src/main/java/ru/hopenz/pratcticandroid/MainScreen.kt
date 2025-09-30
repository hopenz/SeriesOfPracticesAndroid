package ru.hopenz.pratcticandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import org.koin.java.KoinJavaComponent.inject
import ru.hopenz.pratcticandroid.gp.presentation.MockData
import ru.hopenz.pratcticandroid.gp.presentation.screen.CharacterDetailsScreen
import ru.hopenz.pratcticandroid.gp.presentation.screen.CharacterListScreen
import ru.hopenz.pratcticandroid.navigation.Route
import ru.hopenz.pratcticandroid.navigation.TopLevelBackStack

interface TopLevelRoute : Route {
    val icon: ImageVector
}

data object Characters : TopLevelRoute {
    override val icon = Icons.Default.Face
}

data object Faculties : TopLevelRoute {
    override val icon = Icons.AutoMirrored.Filled.List
}

data class CharacterDetails(val characterId: Int) : Route

@Composable
fun MainScreen() {
    val topLevelBackStack by inject<TopLevelBackStack<Route>>(clazz = TopLevelBackStack::class.java)
    val dialogStrategy = remember { DialogSceneStrategy<Route>() }

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(100.dp),
                containerColor = Color.White
            ) {
                listOf(Characters, Faculties).forEach { route ->
                    NavigationBarItem(
                        icon = { Icon(route.icon, null) },
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
                    CharacterListScreen(topLevelBackStack)
                }

                entry<CharacterDetails>(
                    metadata = DialogSceneStrategy.dialog(DialogProperties())
                ) { route ->
                    val character = MockData.getCharacters()
                        .first { it.index == route.characterId }
                    CharacterDetailsScreen(character = character)
                }

                entry<Faculties> {
                    PlaceholderScreen("Второй экран")
                }
            }
        )
    }
}

@Composable
fun PlaceholderScreen(text: String) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(text = text, color = Color.Black)
    }
}
