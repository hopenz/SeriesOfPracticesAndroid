package ru.hopenz.pratcticandroid.gp.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.hopenz.pratcticandroid.gp.data.local.FavoriteEntity
import ru.hopenz.pratcticandroid.gp.presentation.model.CharacterDetailsViewState
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.CharacterDetailsViewModel
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.FavoritesViewModel
import ru.hopenz.pratcticandroid.uikit.FullscreenError
import ru.hopenz.pratcticandroid.uikit.FullscreenLoading
import ru.hopenz.pratcticandroid.uikit.FullscreenMessage
import ru.hopenz.pratcticandroid.uikit.RatingBar

@SuppressLint("UnrememberedMutableState")
@Composable
fun CharacterDetailsScreen(
    characterIndex: Int,
    onBackClick: () -> Unit = {},
    favoritesViewModel: FavoritesViewModel = koinViewModel()
) {
    key(characterIndex) {
        val viewModel: CharacterDetailsViewModel = koinViewModel { parametersOf(characterIndex) }
        val state by viewModel.state.collectAsState()
        val favorites by favoritesViewModel.favorites.collectAsState()

        val isFavorite by if (state.character != null) {
            favoritesViewModel.isFavoriteFlow(state.character!!.id).collectAsState()
        } else {
            mutableStateOf(false)
        }

        LaunchedEffect(characterIndex) {
            viewModel.loadCharacter(characterIndex)
        }

        if (state.error != null) {
            FullscreenError(
                text = state.error,
                retry = { viewModel.loadCharacter(characterIndex) }
            )
        } else {
            CharacterDetailsContent(
                state = state,
                modifier = Modifier.fillMaxSize(),
                onRatingChanged = viewModel::onRatingChanged,
                onRetry = { viewModel.loadCharacter(characterIndex) },
                onBackClick = onBackClick,
                isFavorite = isFavorite,
                onFavoriteClick = {
                    state.character?.let { character ->
                        if (isFavorite) {
                            favoritesViewModel.removeFavorite(character.id)
                        } else {
                            favoritesViewModel.addFavorite(
                                FavoriteEntity(
                                    id = character.id,
                                    name = character.fullName,
                                    house = character.hogwartsHouse
                                )
                            )
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterDetailsContent(
    state: CharacterDetailsViewState,
    modifier: Modifier = Modifier,
    onRatingChanged: (Float) -> Unit = {},
    onRetry: () -> Unit = {},
    onBackClick: () -> Unit = {},
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {}
) {
    when {
        state.isLoading -> FullscreenLoading()
        state.error != null -> FullscreenError(retry = onRetry, text = state.error)
        state.character == null -> FullscreenMessage("Персонаж не найден")
        else -> CharacterDetailsLoadedContent(
            state = state,
            isFavorite = isFavorite,
            onRatingChanged = onRatingChanged,
            onBackClick = onBackClick,
            onFavoriteClick = onFavoriteClick
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterDetailsLoadedContent(
    state: CharacterDetailsViewState,
    isFavorite: Boolean,
    onRatingChanged: (Float) -> Unit = {},
    onBackClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    state.character?.let { character ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val (backButton, favoriteButton, avatar, name, infoColumn, ratingBarBox) = createRefs()

            IconButton(
                onClick = onBackClick,
                modifier = Modifier.constrainAs(backButton) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.constrainAs(favoriteButton) {
                    top.linkTo(backButton.top)
                    end.linkTo(parent.end)
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }

            GlideImage(
                model = character.imageUrl,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .shadow(6.dp, CircleShape)
                    .constrainAs(avatar) {
                        top.linkTo(backButton.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                    },
                contentScale = ContentScale.Crop
            )

            Text(
                text = character.fullName,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(avatar.top)
                    start.linkTo(avatar.end, margin = 16.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.constrainAs(infoColumn) {
                    top.linkTo(avatar.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(ratingBarBox.top, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
            ) {
                character.nickname?.let { Text("Nickname: $it") }
                character.hogwartsHouse?.let { Text("House: $it") }
                character.interpretedBy?.let { Text("Actor: $it") }
                character.birthdate?.let { Text("Birthdate: $it") }
                if (character.children.isNotEmpty())
                    Text("Children: ${character.children.joinToString()}")
            }

            Box(
                modifier = Modifier.constrainAs(ratingBarBox) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                contentAlignment = Alignment.Center
            ) {
                RatingBar(
                    rating = state.rating,
                    onRatingChanged = onRatingChanged,
                    maxRating = 5
                )
            }
        }
    }
}
