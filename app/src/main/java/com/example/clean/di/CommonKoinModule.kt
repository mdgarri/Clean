package com.example.clean.di

import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

val commonKoinModule = module {
    single<Gson> { Gson() }
    single<Converter.Factory> { GsonConverterFactory.create(get<Gson>()) }
}