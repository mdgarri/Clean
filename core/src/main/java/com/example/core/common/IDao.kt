package com.example.core.common

import androidx.room.*

interface IDao <T: Any> {

    fun get(): List<T>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(itemList: List<T>)

    @Delete
    fun delete(item: T)
}