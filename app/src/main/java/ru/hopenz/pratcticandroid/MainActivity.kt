package ru.hopenz.pratcticandroid

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import ru.hopenz.pratcticandroid.navigation.Route
import ru.hopenz.pratcticandroid.navigation.TopLevelBackStack
import ru.hopenz.pratcticandroid.ui.theme.PratcticAndroidTheme


class MainActivity : ComponentActivity(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PratcticAndroidTheme {
                val topLevelBackStack: TopLevelBackStack<Route> = get()
                MainScreen(topLevelBackStack)
            }
        }
    }
}
