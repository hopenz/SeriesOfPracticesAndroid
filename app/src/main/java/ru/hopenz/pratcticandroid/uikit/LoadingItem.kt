package ru.hopenz.pratcticandroid.uikit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.hopenz.pratcticandroid.ui.theme.PratcticAndroidTheme

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(Spacing.small)
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingItemPreview() {
    PratcticAndroidTheme {
        LoadingItem()
    }
}