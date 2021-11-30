package com.example.clean.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.features.coins.CoinsDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataBasesKoinModule = module {
    single<CoinsDataBase> { getRoomBuilder<CoinsDataBase>(androidContext()).build() }
}

inline fun <reified T: RoomDatabase> getRoomBuilder(context: Context): RoomDatabase.Builder<T> {
    return Room.databaseBuilder(
        context,
        T::class.java,
        T::class.simpleName!!
    )
}