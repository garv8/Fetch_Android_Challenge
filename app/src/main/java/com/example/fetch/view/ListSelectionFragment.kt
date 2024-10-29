package com.example.fetch.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetch.adapter.ListSelectionAdapter
import com.example.fetch.databinding.FragmentListSelectionBinding
import com.example.fetch.viewmodel.ItemViewModel
import com.example.fetch.viewmodel.ViewModelFactory

class ListSelectionFragment : Fragment() {

    private var _binding: FragmentListSelectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ItemViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var adapter: ListSelectionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListSelectionAdapter { listId ->
            // Navigate to ItemListFragment with selected listId
            val action = ListSelectionFragmentDirections.actionListSelectionToItemListFragment(listId)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.groupedItems.observe(viewLifecycleOwner) { groupedItems ->
            adapter.submitList(groupedItems.keys.toList())  // Pass unique listId values to adapter
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                showErrorDialog(it)
                viewModel.clearError() // Clear error after showing the dialog
            }
        }

        viewModel.getItems() // Fetch and group items
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Connection Error")
            .setMessage(message)
            .setPositiveButton("Retry") { _, _ ->
                viewModel.getItems() // Retry fetching items
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
