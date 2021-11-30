package com.example.data.features.coins

import androidx.room.Dao
import androidx.room.Query
import com.example.core.common.IDao
import com.example.data.features.coins.entites.CoinEntity

@Dao
interface CoinsDao: IDao<CoinEntity> {

    @Query("SELECT * FROM CoinEntity")
    override fun get(): List<CoinEntity>

}