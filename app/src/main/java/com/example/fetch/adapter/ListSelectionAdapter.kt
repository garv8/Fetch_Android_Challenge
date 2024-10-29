package com.example.fetch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fetch.databinding.ItemListIdBinding

class ListSelectionAdapter(
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<ListSelectionAdapter.ListIdViewHolder>() {

    private val listIds = mutableListOf<Int>()

    fun submitList(list: List<Int>) {
        listIds.clear()
        listIds.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListIdViewHolder {
        val binding = ItemListIdBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListIdViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListIdViewHolder, position: Int) {
        val listId = listIds[position]
        holder.bind(listId, onClick)
    }

    override fun getItemCount() = listIds.size

    class ListIdViewHolder(private val binding: ItemListIdBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listId: Int, onClick: (Int) -> Unit) {
            binding.listIdText.text = "List ID: $listId"
            binding.root.setOnClickListener { onClick(listId) }
        }
    }
}
