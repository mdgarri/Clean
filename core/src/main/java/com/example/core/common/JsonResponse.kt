package com.example.core.common

interface JsonResponse <out T: Any> {
    fun toModel(): T
}