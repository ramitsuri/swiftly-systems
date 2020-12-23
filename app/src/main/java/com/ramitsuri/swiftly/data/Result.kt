package com.ramitsuri.swiftly.data

sealed class Result<T> {
    class Loading<T> : Result<T>()

    data class Success<T>(val data: T) : Result<T>()

    data class Error<T>(val message: String) : Result<T>()

    companion object {
        fun <T> loading() = Loading<T>()

        fun <T> success(data: T) = Success(data)

        fun <T> error(message: String) = Error<T>(message)
    }
}