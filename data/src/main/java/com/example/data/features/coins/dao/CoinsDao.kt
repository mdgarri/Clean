package com.example.data.features.coins.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.common.IDao
import com.example.data.features.coins.entites.CoinEntity

@Dao
interface CoinsDao {

    @Query("SELECT * FROM CoinEntity")
    suspend fun get(): List<CoinEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemList: List<CoinEntity>)

}