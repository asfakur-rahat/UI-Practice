package com.ar.ui_practice.presentation.top_up.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.ui_practice.adapter.top_up.OfferAdapter
import com.ar.ui_practice.data.model.Offer
import com.ar.ui_practice.databinding.ViewpagerItemBinding
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

class OfferViewPage: Fragment() {
    private var _binding: ViewpagerItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: OfferAdapter
    private lateinit var items: List<Offer>

    companion object {
        private const val ARG_ITEMS = "items"
        private const val ARG_PRICE = "price"
        private const val ARG_LIMIT = "limit"

        fun newInstance(items: List<Offer>) = OfferViewPage().apply {
            arguments = Bundle().apply {
                putString(ARG_ITEMS, Json.encodeToString(items))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        items = decodeFromString(arguments?.getString(ARG_ITEMS)?: "")
        println(items)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ViewpagerItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.verticalRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = OfferAdapter {

        }
        binding.verticalRecyclerView.adapter = adapter
        adapter.submitList(items)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}