package com.example.data.features.coins

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.models.CoinDetails
import com.example.data.features.coins.dao.CoinDetailsDao
import com.example.data.features.coins.dao.CoinsDao
import com.example.data.features.coins.entites.CoinDetailsEntity
import com.example.data.features.coins.entites.CoinEntity

@Database(entities = [CoinEntity::class, CoinDetailsEntity::class], version = 2, exportSchema = false)
abstract class CoinsDataBase: RoomDatabase() {
    abstract fun coinsDao(): CoinsDao
    abstract fun coinDetailsDao(): CoinDetailsDao
}