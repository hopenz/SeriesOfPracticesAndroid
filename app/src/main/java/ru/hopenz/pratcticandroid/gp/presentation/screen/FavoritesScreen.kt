package ru.hopenz.pratcticandroid.gp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.hopenz.pratcticandroid.gp.data.local.FavoriteEntity
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.FavoritesViewModel
import ru.hopenz.pratcticandroid.navigation.CharacterDetails
import ru.hopenz.pratcticandroid.navigation.Route
import ru.hopenz.pratcticandroid.navigation.TopLevelBackStack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    topLevelBackStack: TopLevelBackStack<Route>,
    viewModel: FavoritesViewModel = koinViewModel()
) {
    val favorites = viewModel.favorites.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorites") })
        }
    ) { padding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No favorites yet")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favorites, key = { it.id }) { favorite ->
                    FavoriteItem(
                        favorite = favorite,
                        onRemove = { viewModel.removeFavorite(favorite.id) },
                        onClick = { topLevelBackStack.add(CharacterDetails(favorite.id)) }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(
    favorite: FavoriteEntity,
    onRemove: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(favorite.name, style = MaterialTheme.typography.titleMedium)
            favorite.house?.let {
                Text(it, style = MaterialTheme.typography.bodyMedium)
            }
        }
        IconButton(onClick = { onRemove() }) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Remove from favorites",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
