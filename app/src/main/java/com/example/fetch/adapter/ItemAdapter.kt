package com.example.fetch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fetch.databinding.ItemLayoutBinding
import com.example.fetch.model.Item

class ItemAdapter(
    private val saveOrUnsaveAction: (Item) -> Unit
) : ListAdapter<Item, ItemAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), saveOrUnsaveAction)
    }

    class ItemViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item, saveOrUnsaveAction: (Item) -> Unit) {
            binding.itemName.text = item.name
            binding.listId.text = "List ID: ${item.listId}"

            // Set button text based on saved status
            binding.saveButton.text = if (item.isSaved) "Unsave" else "Save"

            // Set up click listener to save or unsave
            binding.saveButton.setOnClickListener {
                saveOrUnsaveAction(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem
    }
}
