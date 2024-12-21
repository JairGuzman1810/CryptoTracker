package com.app.cryptotracker.core.domain.util

/**
 * DomainError is a type alias for the Error interface.
 *
 * This alias is used to represent errors that occur within the domain layer.
 */
typealias DomainError = Error

/**
 * Result is a sealed interface that represents the outcome of an operation.
 *
 * This interface is used to handle both successful and error results in a type-safe way.
 * It is designed to be used throughout the domain layer to represent the outcome of operations.
 *
 * @param D The type of the data in case of success.
 * @param E The type of the error in case of failure, must be a subtype of [Error].
 */
sealed interface Result<out D, out E : Error> {
    /**
     * Success represents a successful result.
     *
     * This data class holds the data returned by a successful operation.
     *
     * @param D The type of the data.
     * @property data The data returned by the successful operation.
     */
    data class Success<out D>(val data: D) : Result<D, Nothing>

    /**
     * Error represents an error result.
     *
     * This data class holds the error returned by a failed operation.
     *
     * @param E The type of the error, must be a subtype of [DomainError].
     * @property error The error returned by the failed operation.
     */
    data class Error<out E : DomainError>(val error: E) : Result<Nothing, E>
}

/**
 * map transforms the data of a successful Result.
 *
 * This inline function allows you to transform the data of a successful Result using the provided map function.
 * If the Result is an Error, it returns the same Error.
 *
 * @param T The type of the original data.
 * @param E The type of the error.
 * @param R The type of the transformed data.
 * @param map The function used to transform the data.
 * @receiver The Result object to be transformed.
 * @return A new Result object with the transformed data or the same error.
 */
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

/**
 * asEmptyDataResult converts a Result to an EmptyResult.
 *
 * This function converts a Result to an EmptyResult, which is a Result with Unit as the data type.
 * It is useful when you only care about whether an operation succeeded or failed, not the data it returned.
 *
 * @param T The type of the original data.
 * @param E The type of the error.
 * @receiver The Result object to be converted.
 * @return An EmptyResult object.
 */
fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

/**
 * onSuccess performs an action if the Result is a Success.
 *
 * This inline function allows you to perform an action if the Result is a Success.
 * If the Result is an Error, it does nothing.
 *
 * @param T The type of the data.
 * @param E The type of the error.
 * @param action The action to be performed if the Result is a Success.
 * @receiver The Result object to perform the action on.
 * @return The same Result object.
 */
inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

/**
 * onError performs an action if the Result is an Error.
 *
 * This inline function allows you to perform an action if the Result is an Error.
 * If the Result is a Success, it does nothing.
 *
 * @param T The type of the data.
 * @param E The type of the error.
 * @param action The action to be performed if the Result is an Error.
 * @receiver The Result object to perform the action on.
 * @return The same Result object.
 */
inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

/**
 * EmptyResult is a type alias for Result<Unit, E>.
 *
 * This alias is used to represent a Result that does not return any data, only success or failure.
 * It is useful when you only care about whether an operation succeeded or failed, not the data it returned.
 *
 * @param E The type of the error.
 */
typealias EmptyResult<E> = Result<Unit, E>