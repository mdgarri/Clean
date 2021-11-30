package com.example.core.common

interface ConvertibleToModel <out T: Any> {
    fun toModel(): T
}