package com.example.fetch.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.fetch.repository.ItemRepository
import com.example.fetch.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    private val _groupedItems = MutableLiveData<Map<Int, List<Item>>>()
    val groupedItems: LiveData<Map<Int, List<Item>>> = _groupedItems
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getItems() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val allItems = repository.fetchItems().filter { it.name?.isNotBlank() == true }
                val groupedAndSortedItems = allItems
                    .groupBy { it.listId }
                    .mapValues { entry -> entry.value.sortedBy { it.name } }

                _groupedItems.postValue(groupedAndSortedItems)
                _error.postValue(null) // Clear any previous errors
                _loading.postValue(false)
            } catch (e: Exception) {
                _error.postValue("Failed to load data. Please check your connection.")
                _loading.postValue(false)
            }

        }
    }

    fun clearError() {
        _error.value = null
    }

    fun saveOrUnsaveItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            item.isSaved = !item.isSaved  // Toggle the saved state first

            if (item.isSaved) {
                repository.saveItem(item)
            } else {
                repository.unsaveItem(item)
            }
            getItems() // Refresh list
        }
    }

    fun getSavedItems(): LiveData<List<Item>> {
        return repository.getSavedItems().asLiveData()
    }
}

class ViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val repository = ItemRepository(context)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
