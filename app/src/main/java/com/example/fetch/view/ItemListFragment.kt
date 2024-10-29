package com.example.fetch.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetch.databinding.FragmentItemListBinding
import com.example.fetch.viewmodel.ItemViewModel
import com.example.fetch.viewmodel.ViewModelFactory
import com.example.fetch.adapter.ItemAdapter

class ItemListFragment : Fragment() {

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ItemViewModel by viewModels { ViewModelFactory(requireContext()) }
    private val args: ItemListFragmentArgs by navArgs()
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ItemAdapter(viewModel::saveOrUnsaveItem)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.getItems()
        // Observe items for selected listId
        viewModel.groupedItems.observe(viewLifecycleOwner) { groupedItems ->
            val itemsForListId = groupedItems[args.listId] ?: emptyList()
            Log.d("test", "$itemsForListId")
            adapter.submitList(itemsForListId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
