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
import java.util.ArrayList

class OfferListFragment :Fragment() {
    private var _binding: ViewpagerItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: OfferListAdapter
    private lateinit var items: List<Offer>

    companion object{
        private const val ARG_PRICE = "price"
        private const val ARG_LIMIT = "expire"
        private const val ARG_AMOUNT = "items"
        private const val ARG_ICON = "icon"

        fun newInstance(items: List<Offer>) = OfferListFragment().apply {
            arguments = Bundle().apply {
                putStringArrayList(ARG_PRICE, ArrayList(items.map { it.price }))
                putStringArrayList(ARG_LIMIT, ArrayList(items.map { it.expire }))
                putStringArrayList(ARG_AMOUNT, ArrayList(items.map { it.bundle.map {lol-> lol.amount } }))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //items = Json.decode(arguments?.getString(ARG_ITEMS)) ?: emptyList()
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