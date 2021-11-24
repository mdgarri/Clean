package com.example.core.common

import java.lang.Exception

object Exceptions {

    class FailureException(message: String?) : Exception(message ?: "")
    class NetworkException() : Exception()
    class EmptyResponseException() : Exception()
    class ServerException(message: String?) : Exception(message ?: "")
    class RequestErrorException(message: String?) : Exception(message ?: "")
}