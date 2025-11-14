package ru.hopenz.pratcticandroid.profile.presentation.screen

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.hopenz.pratcticandroid.navigation.TopLevelBackStack
import ru.hopenz.pratcticandroid.profile.presentation.receiver.PairNotificationReceiver
import ru.hopenz.pratcticandroid.profile.presentation.viewModel.ProfileViewModel
import java.util.Calendar
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    topLevelBackStack: TopLevelBackStack<*>,
    viewModel: ProfileViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val ctx = LocalContext.current
    val profile by viewModel.profile.collectAsState()

    var fullName by rememberSaveable { mutableStateOf(profile.fullName) }
    var position by rememberSaveable { mutableStateOf(profile.position ?: "") }
    var avatarUri by rememberSaveable { mutableStateOf(profile.avatarUri?.toUri()) }

    var favoritePairTime by rememberSaveable { mutableStateOf(profile.favoritePairTime ?: "") }
    var timeError by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    val timePickerDialog = TimePickerDialog(
        ctx,
        { _, hour, minute ->
            favoritePairTime = String.format("%02d:%02d", hour, minute)
            timeError = false
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

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
                        val timePattern = Pattern.compile("^([01]\\d|2[0-3]):[0-5]\\d$")
                        if (favoritePairTime.isNotEmpty() && !timePattern.matcher(favoritePairTime)
                                .matches()
                        ) {
                            timeError = true
                            return@TextButton
                        } else {
                            timeError = false
                        }

                        viewModel.saveProfile(
                            profile.copy(
                                fullName = fullName,
                                position = position.ifBlank { null },
                                avatarUri = avatarUri?.toString(),
                                favoritePairTime = favoritePairTime.ifBlank { null }
                            )
                        )

                        if (favoritePairTime.isNotEmpty()) {
                            scheduleNotification(ctx, fullName, favoritePairTime)
                        }

                        onBackClick()
                    }) {
                        Text("Готово")
                    }
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
                    .clickable { /* TODO: выбор аватара */ },
                contentAlignment = Alignment.Center
            ) {
                if (avatarUri != null)
                    AsyncImage(model = avatarUri, contentDescription = "Avatar")
                else
                    Text("A", style = MaterialTheme.typography.titleLarge)
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("ФИО") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = position,
                onValueChange = { position = it },
                label = { Text("Должность") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Text("Напоминание:", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = favoritePairTime,
                onValueChange = { favoritePairTime = it },
                label = { Text("Время любимой пары (HH:mm)") },
                placeholder = { Text("Введите время или выберите часы") },
                trailingIcon = {
                    IconButton(onClick = { timePickerDialog.show() }) {
                        Icon(Icons.Default.AccessTime, contentDescription = "Выбрать время")
                    }
                },
                singleLine = true,
                isError = timeError,
                supportingText = {
                    if (timeError) Text(
                        "Введите корректное время (HH:mm)",
                        color = MaterialTheme.colorScheme.error
                    )
                },
                keyboardOptions = KeyboardOptions.Default,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
    }
}

fun scheduleNotification(context: Context, name: String, time: String) {
    val parts = time.split(":")
    val hour = parts.getOrNull(0)?.toIntOrNull() ?: return
    val minute = parts.getOrNull(1)?.toIntOrNull() ?: return

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        if (before(Calendar.getInstance())) add(Calendar.DAY_OF_MONTH, 1)
    }

    val intent = Intent(context, PairNotificationReceiver::class.java).apply {
        putExtra("name", name)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        System.currentTimeMillis().toInt(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
        val i = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        i.data = Uri.parse("package:${context.packageName}")
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
        return
    }

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}
