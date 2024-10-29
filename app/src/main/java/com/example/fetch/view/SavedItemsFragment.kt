package com.example.fetch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetch.databinding.FragmentSavedItemsBinding
import com.example.fetch.viewmodel.ItemViewModel
import com.example.fetch.viewmodel.ViewModelFactory
import com.example.fetch.adapter.ItemAdapter

class SavedItemsFragment : Fragment() {

    private var _binding: FragmentSavedItemsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ItemViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSavedItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ItemAdapter(viewModel::saveOrUnsaveItem)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.getSavedItems().observe(viewLifecycleOwner) { savedItems ->
            adapter.submitList(savedItems)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
