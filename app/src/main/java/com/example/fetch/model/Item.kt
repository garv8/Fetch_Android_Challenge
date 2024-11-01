package com.example.fetch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey val id: Int,
    val listId: Int,
    val name: String?,
    var isSaved: Boolean = false
)
