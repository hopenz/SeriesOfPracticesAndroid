package ru.hopenz.pratcticandroid.gp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.hopenz.pratcticandroid.gp.presentation.model.CharacterDetailsViewState
import ru.hopenz.pratcticandroid.gp.presentation.model.CharacterUiModel
import ru.hopenz.pratcticandroid.gp.presentation.viewModel.CharacterDetailsViewModel
import ru.hopenz.pratcticandroid.uikit.RatingBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(
    character: CharacterUiModel,
) {
    val viewModel = koinViewModel<CharacterDetailsViewModel>(
        key = "CharacterDetails_${character.index}"
    ) {
        parametersOf(character.index)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(state.character.fullName) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    val context = LocalContext.current
                    IconButton(onClick = {
                        shareText(context, state.character.fullName)
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                }
            )
        }
    ) { padding ->
        CharacterDetailsContent(
            state = state,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            onRatingChanged = viewModel::onRatingChanged,
            onFavoriteToggled = viewModel::onFavoriteToggled
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterDetailsContent(
    state: CharacterDetailsViewState,
    modifier: Modifier = Modifier,
    onRatingChanged: (Float) -> Unit = {},
    onFavoriteToggled: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (avatar, name, infoColumn, ratingBar) = createRefs()

        GlideImage(
            model = state.character.imageUrl,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .shadow(6.dp, CircleShape)
                .constrainAs(avatar) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                },
            contentScale = ContentScale.Crop
        )

        Text(
            text = state.character.fullName,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(avatar.top)
                start.linkTo(avatar.end, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.constrainAs(infoColumn) {
                top.linkTo(avatar.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                bottom.linkTo(ratingBar.top, margin = 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
            }
        ) {
            if (!state.character.nickname.isNullOrBlank()) {
                Text("Nickname: ${state.character.nickname}", fontWeight = FontWeight.Bold)
            }
            if (!state.character.hogwartsHouse.isNullOrBlank()) {
                Text("Faculty: ${state.character.hogwartsHouse}", fontWeight = FontWeight.Bold)
            }
            if (!state.character.interpretedBy.isNullOrBlank()) {
                Text("Actor: ${state.character.interpretedBy}", fontWeight = FontWeight.Bold)
            }
            if (!state.character.birthdate.isNullOrBlank()) {
                Text("Birthdate: ${state.character.birthdate}", fontWeight = FontWeight.Bold)
            }
            if (state.character.children.isNotEmpty()) {
                Text(
                    "Children: ${state.character.children.joinToString()}",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box(
            modifier = Modifier.constrainAs(ratingBar) {
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


