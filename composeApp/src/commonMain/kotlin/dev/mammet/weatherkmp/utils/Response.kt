package dev.mammet.weatherkmp.utils

import coil3.map.Mapper

sealed class Response<out T, out E> {
    data class Success<out T>(val data: T): Response<T, Nothing>()
    sealed class Error<E> : Response<Nothing, E>(){
        data class HttpError<E>(val code: Int, val errorBody: E?) : Error<E>()
        data object NetworkError : Error<Nothing>()
        data object SerializationError: Error<Nothing>()
        data object DefaultError: Error<Nothing>()
    }
    data object Loading: Response<Nothing, Nothing>()
}

inline fun <T, E, R> Response<T, E>.map(mapper: (T) -> R): Response<R, E> = when(this){
    is Response.Success -> Response.Success(mapper(this.data))
    is Response.Error -> this
    is Response.Loading -> this
}