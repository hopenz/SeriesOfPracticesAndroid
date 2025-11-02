package ru.hopenz.pratcticandroid.gp.utils


import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class AppError(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause) {
    data class NetworkError(val errorType: NetworkErrorType) : AppError(errorType.message)
    data class DataError(override val message: String) : AppError(message)
    data class UnknownError(override val message: String) : AppError(message)
}

enum class NetworkErrorType(val message: String) {
    NO_INTERNET("Нет подключения к интернету"),
    TIMEOUT("Превышено время ожидания ответа"),
    NETWORK("Ошибка сети")
}

fun Throwable.toAppError(): AppError {
    return when (this) {
        is UnknownHostException -> AppError.NetworkError(NetworkErrorType.NO_INTERNET)
        is SocketTimeoutException -> AppError.NetworkError(NetworkErrorType.TIMEOUT)
        is IOException -> AppError.NetworkError(NetworkErrorType.NETWORK)
        is AppError -> this
        else -> AppError.UnknownError(this.message ?: "Неизвестная ошибка")
    }
}

fun handleCommonErrors(
    error: Throwable,
    onError: (String) -> Unit
) {
    val appError = error.toAppError()
    when (appError) {
        is AppError.NetworkError -> onError(appError.errorType.message)
        is AppError.DataError -> onError(appError.message)
        is AppError.UnknownError -> onError(appError.message)
    }
}