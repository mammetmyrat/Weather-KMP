package dev.mammet.weatherkmp.common.data

import dev.mammet.weatherkmp.utils.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException

inline fun <reified T, reified E> HttpClient.safeRequest(
    crossinline block: HttpRequestBuilder.() -> Unit,
): Flow<Response<T, E>> = flow<Response<T, E>> {
    emit(Response.Loading)
    val response = request { block() }
    emit(Response.Success(response.body<T>()))
}

suspend inline fun <reified E> ResponseException.errorBody(): E? =
    try {
        response.body()
    }catch (e: SerializationException){
        null
    }