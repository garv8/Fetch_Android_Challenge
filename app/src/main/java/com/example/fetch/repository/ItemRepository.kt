package com.example.fetch.repository

import android.content.Context
import com.example.fetch.model.AppDatabase
import com.example.fetch.model.Item
import com.example.fetch.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class ItemRepository(context: Context) {

    private val api = RetrofitInstance.api
    private val db = AppDatabase.getInstance(context)

    suspend fun fetchItems(): List<Item> {
        val items = api.getItems()
            .filter { !it.name.isNullOrEmpty() }
            .sortedWith(compareBy({ it.listId }, { it.name }))

        // Mark saved items as saved from the DB
        val savedItems = db.itemDao().getSavedItemsIds()
        items.forEach { it.isSaved = savedItems.contains(it.id) }

        return items
    }

    suspend fun saveItem(item: Item) {
        db.itemDao().insert(item)
    }

    suspend fun unsaveItem(item: Item) {
        db.itemDao().delete(item)
    }

    fun getSavedItems(): Flow<List<Item>> {
        return db.itemDao().getAllSavedItems()
    }
}
