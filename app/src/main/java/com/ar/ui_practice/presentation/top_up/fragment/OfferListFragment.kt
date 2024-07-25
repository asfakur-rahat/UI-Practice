package com.ar.ui_practice.presentation.top_up.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.ui_practice.adapter.top_up.OfferListAdapter
import com.ar.ui_practice.data.model.top_up.Offer
import com.ar.ui_practice.databinding.ViewpagerItemBinding
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class OfferListFragment :Fragment() {
    private var _binding: ViewpagerItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: OfferListAdapter
    private lateinit var items: List<Offer>

    companion object{
        private const val ARG_ITEMS = "items"

        fun newInstance(items: List<Offer>) = OfferListFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_ITEMS, Json.encodeToString(items))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        items = Json.decodeFromString(arguments?.getString(ARG_ITEMS) ?: "")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ViewpagerItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.verticalRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = OfferListAdapter {

        }
        binding.verticalRecyclerView.adapter = adapter
        adapter.submitList(items)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}