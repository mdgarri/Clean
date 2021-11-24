package com.example.data

import com.example.core.common.NetResult
import extensions.fromJsonToObject
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

private val SUCCESSFULLY = 200 until 300
private val INTERNAL_SERVER_ERROR = 500 until 527
private val BAD_REQUEST = 400 until 500

class CallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit) =
        when (getRawType(returnType)) {
            Call::class.java -> {
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                when (getRawType(callType)) {
                    NetResult::class.java -> {
                        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                        ResultAdapter(resultType)
                    }
                    else -> null
                }
            }
            else -> null
        }
}

class ResultAdapter(private val type: Type) : CallAdapter<Type, Call<NetResult<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<NetResult<Type>> = ResultCall(call)
}

class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, NetResult<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<NetResult<T>>) =
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val result = when (response.code()) {
                    in SUCCESSFULLY -> {
                        response.body()?.let { body ->
                            NetResult.Success(body)
                        } ?: run {
                            NetResult.Error.EmptyResponse
                        }
                    }
                    in INTERNAL_SERVER_ERROR -> {
                        tryToParseError(response.errorBody())
                    }
                    in BAD_REQUEST -> {
                        tryToParseError(response.errorBody())
                    }
                    else -> {
                        tryToParseError(response.errorBody())
                    }
                }

                callback.onResponse(this@ResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = when (t) {
                    is HttpException, is IOException -> {
                        NetResult.Error.NetworkError
                    }
                    else -> {
                        NetResult.Error.Failure(t.message, null)
                    }
                }
                t.printStackTrace()
                callback.onResponse(this@ResultCall, Response.success(result))
            }
        })

    override fun cloneImpl() = ResultCall(proxy.clone())

    override fun timeout(): Timeout {
        return Timeout()
    }

    private fun tryToParseError(errorBody: ResponseBody?): NetResult.Error {
        return errorBody?.string()?.let { text ->
            val body = fromJsonToObject<ErrorBody>(text)
            NetResult.Error.Failure(body?.message, body?.statusCode)
        } ?: run {
            NetResult.Error.Failure(null, null)
        }
    }
}

abstract class CallDelegate<TIn, TOut>(protected val proxy: Call<TIn>) : Call<TOut> {
    override fun execute(): Response<TOut> = throw NotImplementedError()
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    final override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun cloneImpl(): Call<TOut>
}

data class ErrorBody(val message: String, val statusCode: Int)