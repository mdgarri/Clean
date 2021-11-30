package com.example.data.features.coins

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.features.coins.entites.CoinEntity

@Database(entities = [CoinEntity::class], version = 1, exportSchema = false)
abstract class CoinsDataBase: RoomDatabase() {
    abstract fun coinsDao(): CoinsDao
}