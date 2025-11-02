package ru.hopenz.pratcticandroid.profile.presentation.screen

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.koin.compose.viewmodel.koinViewModel
import ru.hopenz.pratcticandroid.navigation.EditProfile
import ru.hopenz.pratcticandroid.navigation.Route
import ru.hopenz.pratcticandroid.navigation.TopLevelBackStack
import ru.hopenz.pratcticandroid.profile.presentation.viewModel.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    topLevelBackStack: TopLevelBackStack<Route>,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val profile by viewModel.profile.collectAsState()
    val ctx = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль") },
                actions = {
                    IconButton(onClick = { topLevelBackStack.add(EditProfile) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Редактировать")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                if (profile.avatarUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(ctx)
                            .data(profile.avatarUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .clickable { topLevelBackStack.add(EditProfile) }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { topLevelBackStack.add(EditProfile) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("A", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text("ФИО", style = MaterialTheme.typography.labelMedium)
            Text(profile.fullName.ifBlank { "Не задано" }, style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(8.dp))

            Text("Должность", style = MaterialTheme.typography.labelMedium)
            Text(profile.position ?: "Не указана", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                viewModel.openResume(profile.resumeUrl,
                    onError = { msg -> Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show() },
                    openIntent = { intent ->
                        if (profile.resumeUrl?.startsWith("http") == true) {
                            startDownload(ctx, profile.resumeUrl!!)
                        }
                        try {
                            ctx.startActivity(intent)
                        } catch (t: Exception) {
                            Toast.makeText(ctx, "Нет подходящего приложения для открытия файла", Toast.LENGTH_SHORT).show()
                        }
                    })
            }) {
                Text("Резюме")
            }
        }
    }
}

private fun startDownload(context: Context, url: String) {
    try {
        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setTitle("Загрузка резюме")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        dm.enqueue(request)
    } catch (_: Throwable) {
    }
}