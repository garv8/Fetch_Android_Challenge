package com.example.fetch.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM items WHERE isSaved = 1")
    fun getAllSavedItems(): Flow<List<Item>>

    @Query("SELECT id FROM items WHERE isSaved = 1")
    suspend fun getSavedItemsIds(): List<Int>
}
