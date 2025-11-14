package ru.hopenz.pratcticandroid.gp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import java.util.Calendar
import ru.hopenz.pratcticandroid.profile.presentation.receiver.PairNotificationReceiver

fun scheduleNotification(context: Context, name: String, time: String) {
    val parts = time.split(":")
    val hour = parts[0].toIntOrNull() ?: return
    val minute = parts[1].toIntOrNull() ?: return

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

    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
}