package ru.hopenz.pratcticandroid.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList

class TopLevelBackStack<T : Route>(startKey: T) {

    private val topLevelStacks: LinkedHashMap<T, SnapshotStateList<T>> =
        linkedMapOf(startKey to mutableStateListOf(startKey))

    var topLevelKey by mutableStateOf(startKey)
        private set

    val backStack = mutableStateListOf(startKey)

    private fun updateBackStack() {
        backStack.clear()
        backStack.addAll(topLevelStacks[topLevelKey] ?: mutableStateListOf())
    }

    fun addTopLevel(key: T) {
        if (!topLevelStacks.containsKey(key)) {
            topLevelStacks[key] = mutableStateListOf(key)
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T) {
        val currentStack = topLevelStacks[topLevelKey]
        if (currentStack != null) {
            currentStack.add(key)
        } else {
            topLevelStacks[topLevelKey] = mutableStateListOf(topLevelKey, key)
        }
        updateBackStack()
    }

    fun removeLast() {
        val currentStack = topLevelStacks[topLevelKey] ?: return

        if (currentStack.size > 1) {
            currentStack.removeAt(currentStack.lastIndex)
        } else {
            topLevelStacks.remove(topLevelKey)
            topLevelKey = topLevelStacks.keys.lastOrNull() ?: return
        }

        updateBackStack()
    }

    fun clearAll() {
        topLevelStacks.clear()
        backStack.clear()
    }
}