package ru.hopenz.pratcticandroid.profile.presentation.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.hopenz.pratcticandroid.gp.utils.FileUtils
import ru.hopenz.pratcticandroid.navigation.TopLevelBackStack
import ru.hopenz.pratcticandroid.profile.presentation.viewModel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    topLevelBackStack: TopLevelBackStack<*>,
    viewModel: ProfileViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val ctx = LocalContext.current
    val profile by viewModel.profile.collectAsState()

    var fullName by remember { mutableStateOf(profile.fullName) }
    var position by remember { mutableStateOf(profile.position ?: "") }
    var resumeUrl by remember { mutableStateOf(profile.resumeUrl ?: "") }
    var avatarUri by remember { mutableStateOf(profile.avatarUri?.toUri()) }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    var showPickDialog by remember { mutableStateOf(false) }
    var pendingAction by remember { mutableStateOf<(() -> Unit)?>(null) }

    val pickGalleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { avatarUri = it }
    }

    val takePhotoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) avatarUri = tempCameraUri
        tempCameraUri = null
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            pendingAction?.invoke()
        }
        pendingAction = null
    }

    fun onGalleryClick() {
        pendingAction = { pickGalleryLauncher.launch("image/*") }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    fun onCameraClick() {
        pendingAction = {
            val uri = FileUtils.createTempImageUri(ctx)
            tempCameraUri = uri
            takePhotoLauncher.launch(uri)
        }
        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Редактировать профиль") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        viewModel.saveProfile(
                            profile.copy(
                                fullName = fullName,
                                position = position.ifBlank { null },
                                resumeUrl = resumeUrl.ifBlank { null },
                                avatarUri = avatarUri?.toString()
                            )
                        )
                        onBackClick()
                    }) { Text("Готово") }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { showPickDialog = true },
                contentAlignment = Alignment.Center
            ) {
                if (avatarUri != null) AsyncImage(model = avatarUri, contentDescription = "Avatar")
                else Text("A", style = MaterialTheme.typography.titleLarge)
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("ФИО") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = position,
                onValueChange = { position = it },
                label = { Text("Должность") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = resumeUrl,
                onValueChange = { resumeUrl = it },
                label = { Text("Ссылка на резюме") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (showPickDialog) {
            AlertDialog(
                onDismissRequest = { showPickDialog = false },
                title = { Text("Выберите аватар") },
                text = {
                    Column {
                        Text(
                            "Галерея",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showPickDialog = false
                                    onGalleryClick()
                                }
                                .padding(16.dp)
                        )
                        Text(
                            "Камера",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showPickDialog = false
                                    onCameraClick()
                                }
                                .padding(16.dp)
                        )
                    }
                },
                confirmButton = {},
                dismissButton = {}
            )
        }
    }
}