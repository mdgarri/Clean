package com.example.data.features.coins.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.common.IDao
import com.example.core.models.CoinDetails
import com.example.data.features.coins.entites.CoinDetailsEntity

@Dao
interface CoinDetailsDao {

    @Query("SELECT * FROM CoinDetailsEntity WHERE id = :coindId")
    suspend fun get(coindId: String): CoinDetailsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CoinDetailsEntity)
}