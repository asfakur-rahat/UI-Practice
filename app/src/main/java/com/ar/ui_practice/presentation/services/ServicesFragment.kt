package com.ar.ui_practice.presentation.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.ui_practice.R
import com.ar.ui_practice.adapter.service.ServiceCategoryAdapter
import com.ar.ui_practice.data.demo.DemoData
import com.ar.ui_practice.databinding.FragmentServicesBinding

class ServicesFragment : Fragment(R.layout.fragment_services) {

    private lateinit var binding: FragmentServicesBinding
    private lateinit var adaptar: ServiceCategoryAdapter
    private var servicesList = DemoData.ServiceCategories

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentServicesBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initView()
    }

    private fun initAdapter() {
        adaptar = ServiceCategoryAdapter{ _ ->
            findNavController().navigate(R.id.action_servicesFragment_to_contactsFragment)
        }
    }

    private fun initView() {
        binding.actionBar.tvNavTitle.text = "Services"
        binding.actionBar.ivNavIcon.visibility = View.GONE

        binding.rvCategory.adapter = adaptar
        binding.rvCategory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        adaptar.submitList(servicesList)
    }
}