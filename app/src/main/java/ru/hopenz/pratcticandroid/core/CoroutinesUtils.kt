package ru.hopenz.pratcticandroid.core

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


fun CoroutineScope.launchWithLoadingAndError(
    onError: (Throwable) -> Unit = {},
    onLoading: (Boolean) -> Unit = {},
    block: suspend CoroutineScope.() -> Unit,
): Job {
    val context = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    } + LoadingContext(onLoading)

    return launch(context) {
        runWithLoading(this, block)
    }
}


class LoadingContext(
    private val onLoading: (Boolean) -> Unit
) : CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = Key

    companion object Key : CoroutineContext.Key<LoadingContext>

    fun show() = onLoading(true)
    fun hide() = onLoading(false)
}


private suspend fun <T> runWithLoading(
    coroutineScope: CoroutineScope,
    block: suspend CoroutineScope.() -> T
): T {
    return coroutineScope.runCatching {
        coroutineScope.coroutineContext[LoadingContext]?.show()
        block()
    }.also {
        coroutineScope.coroutineContext[LoadingContext]?.hide()
    }.getOrThrow()
}