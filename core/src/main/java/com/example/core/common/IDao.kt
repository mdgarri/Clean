package com.example.core.common

import androidx.room.*

interface IDao <T: Any> {

    suspend fun get(): List<T>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemList: List<T>)

    @Delete
    suspend fun delete(item: T)
}