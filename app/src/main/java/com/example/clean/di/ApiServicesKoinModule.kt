package com.example.clean.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.core.common.Constants
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.koin.dsl.module
import java.util.concurrent.TimeUnit
import com.example.core.common.Constants.API_TIMEOUT_VALUE
import com.example.core.common.Constants.API_TIMEOUT_UNIT
import com.example.data.CallAdapterFactory
import com.example.data.features.coins.CoinsAPI
import org.koin.android.ext.koin.androidContext
import retrofit2.Converter
import retrofit2.Retrofit

val apiServicesKoinModule = module {

    single<ChuckerInterceptor> {
        val collector = ChuckerCollector(
            context = androidContext(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_WEEK
        )
        ChuckerInterceptor.Builder(context = androidContext())
            .collector(collector = collector)
            .maxContentLength(length = 2000)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }

    single<CoinsAPI> {
        val okkHttpClient = OkHttpClient.Builder()
            .addInterceptor(get<ChuckerInterceptor>()).build()

        val retroift = Retrofit.Builder()
            .client(okkHttpClient)
            .baseUrl(Constants.COIN_API_URL)
            .addCallAdapterFactory(CallAdapterFactory())
            .addConverterFactory(get<Converter.Factory>())
            .build()

        retroift.create(CoinsAPI::class.java)
    }

}


fun getOkHttpClientBuilder(): OkHttpClient.Builder {
    return OkHttpClient.Builder()
        .connectTimeout(API_TIMEOUT_VALUE, API_TIMEOUT_UNIT)
        .readTimeout(API_TIMEOUT_VALUE, API_TIMEOUT_UNIT)
        .writeTimeout(API_TIMEOUT_VALUE, API_TIMEOUT_UNIT)
        .callTimeout(API_TIMEOUT_VALUE, API_TIMEOUT_UNIT)
        .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
        .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
}
