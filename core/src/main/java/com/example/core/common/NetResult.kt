package com.example.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

sealed class NetResult<out T> {
    object Loading : NetResult<Nothing>()
    data class Success<T>(val data: T) : NetResult<T>()

    sealed class Error(val exception: Exception) : NetResult<Nothing>() {
        object NetworkError : Error(Exceptions.NetworkException())
        object EmptyResponse : Error(Exceptions.EmptyResponseException())
        data class ServerError(val message: String? = null) : Error(Exceptions.ServerException(message))
        data class RequestError(val message: String? = null) : Error(Exceptions.RequestErrorException(message))

        data class Failure(
            val message: String? = null,
            val statusCode: Int? = null
        ) : Error(Exceptions.FailureException(message))
    }

    fun isSuccess(): Boolean = this is Success
    fun isNetworkError(): Boolean = this is Error.NetworkError
    fun isEmptyResponse(): Boolean = this is Error.EmptyResponse
    fun isServerError(): Boolean = this is Error.ServerError
    fun isRequestError(): Boolean = this is Error.RequestError
    fun isFailure(): Boolean = this is Error.Failure

    fun get(): T? =
        when (this) {
            is Success -> data
            else -> null
        }

    fun doOnSuccess(block: (T) -> Unit): NetResult<T> {
        if (this is Success) {
            block(data)
        }
        return this
    }

    fun doOnError(block: (Error) -> Unit): NetResult<T> {
        if (this is Error) {
            block(this)
        }
        return this
    }

    fun doOnLoad(block: () -> Unit): NetResult<T> {
        if (this is Loading) {
            block()
        }
        return this
    }

    suspend fun doOnSuccess(
        context: CoroutineContext,
        block: suspend CoroutineScope.(T) -> Unit
    ): NetResult<T> {
        if (this is Success) {
            withContext(context) { block(data) }
        }
        return this
    }

    suspend fun doOnError(
        context: CoroutineContext,
        block: suspend (Error) -> Unit
    ): NetResult<T> {
        if (this is Error) {
            withContext(context) { block(this@NetResult) }
        }
        return this
    }

    suspend fun doOnLoad(
        context: CoroutineContext,
        block: suspend () -> Unit
    ): NetResult<T> {
        if (this is Loading) {
            withContext(context) { block() }
        }
        return this
    }

}

fun <T : Any> T.asSuccess(): NetResult<T> {
    return NetResult.Success(this)
}

fun String.asFailure(): NetResult<Nothing> {
    return NetResult.Error.Failure()
}


@JvmName("primitiveToRemoteInteractorResult")
fun <M : Any> NetResult<M>.toModel(): NetResult<M> {
    return this
}

fun <M : Any, JR : JsonResponse<M>> NetResult<JR>.toModel(): NetResult<M> {
    return when (this) {
        is NetResult.Loading -> this
        is NetResult.Success -> {
            NetResult.Success(this.data.toModel())
        }
        is NetResult.Error -> this
    }
}

@JvmName("toListRemoteInteractorResult")
fun <M : Any, JR : JsonResponse<M>> NetResult<List<JR>>.toModel(): NetResult<List<M>> {
    return when (this) {
        is NetResult.Loading -> this
        is NetResult.Success -> {
            val mappedList = this.data
                .map {
                    it.toModel()
                }
            NetResult.Success(mappedList)
        }
        is NetResult.Error -> this
    }
}

inline fun <V1 : Any, V2 : Any> NetResult<V1>.map(f: (V1) -> V2): NetResult<V2> =
    when (this) {
        is NetResult.Loading -> this
        is NetResult.Error -> this
        is NetResult.Success -> f(data).asSuccess()
    }

inline fun <V1 : Any, V2 : Any> NetResult<V1>.flatMap(f: (V1) -> NetResult<V2>): NetResult<V2> =
    when (this) {
        is NetResult.Loading -> this
        is NetResult.Error -> this
        is NetResult.Success<V1> -> f(data)
    }

inline fun <V1 : Any, reified V2 : Any?> NetResult<V1>.zip(f: (V1) -> V2): NetResult<Pair<V1, V2>> =
    when (this) {
        is NetResult.Loading -> this
        is NetResult.Error -> this
        is NetResult.Success<V1> -> {
            val newData = f(data)
            (data to newData).asSuccess()
        }
    }


inline fun <V1 : Any, reified V2 : Any> NetResult<V1>.flatZip(f: (V1) -> NetResult<V2>): NetResult<Pair<V1, V2>> =
    when (this) {
        is NetResult.Loading -> this
        is NetResult.Error -> this
        is NetResult.Success<V1> -> {
            f(data).map { data to (it) }
        }
    }

@FlowPreview
fun <T: Any, R: Any> Flow<T>.flatMap(block: (T) -> Flow<R>): Flow<R> = this.flatMapMerge {
    when(it) {
        is NetResult.Error -> throw it.exception
        else -> block(it)
    }
}