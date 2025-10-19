package ru.hopenz.pratcticandroid.gp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.hopenz.pratcticandroid.CharacterDetails
import ru.hopenz.pratcticandroid.gp.presentation.model.CharacterListViewState
import ru.hopenz.pratcticandroid.gp.presentation.model.CharacterUiModel
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.CharacterListViewModel
import ru.hopenz.pratcticandroid.navigation.Route
import ru.hopenz.pratcticandroid.navigation.TopLevelBackStack
import ru.hopenz.pratcticandroid.uikit.FullscreenError
import ru.hopenz.pratcticandroid.uikit.FullscreenLoading

@Composable
fun CharacterListScreen(
    topLevelBackStack: TopLevelBackStack<Route>,
    viewModel: CharacterListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCharacters(lang = "en")
    }

    when (val s = state.state) {
        is CharacterListViewState.State.Loading -> FullscreenLoading()
        is CharacterListViewState.State.Error -> FullscreenError(
            text = s.error,
            retry = { viewModel.loadCharacters(lang = "en") }
        )
        is CharacterListViewState.State.Success -> {
            LazyColumn {
                items(s.data, key = { it.index }) { character ->
                    CharacterListItem(
                        character = character,
                        onCharacterClick = {
                            topLevelBackStack.add(CharacterDetails(character.index))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterListItem(
    character: CharacterUiModel,
    onCharacterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onCharacterClick() }
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = character.fullName,
            style = MaterialTheme.typography.titleMedium,
        )

        if (!character.nickname.isNullOrBlank()) {
            Text(
                text = "Nickname: ${character.nickname}",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        if (!character.hogwartsHouse.isNullOrBlank()) {
            Text(
                text = "Faculty: ${character.hogwartsHouse}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        HorizontalDivider()
    }
}
