package com.ar.ui_practice.adapter.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ar.ui_practice.data.model.Service
import com.ar.ui_practice.data.model.Services
import com.ar.ui_practice.databinding.ServiceCategoryItemBinding

class ServiceCategoryAdapter(
    private val onClick: (Service) -> Unit,
) : ListAdapter<Services, ServiceCategoryAdapter.ServiceCategoryViewHolder>(DiffChecker) {

    class ServiceCategoryViewHolder(
        private val binding: ServiceCategoryItemBinding,
        private val onClick: (Service) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(services: Services) {
            val serviceAdapter = ServiceAdapter(onClick)
            binding.tvCategory.text = services.title
            binding.rvService.adapter = serviceAdapter
            binding.rvService.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context,4)
            }
            serviceAdapter.submitList(services.service)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ServiceCategoryItemBinding.inflate(layoutInflater, parent, false)
        return ServiceCategoryViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ServiceCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DiffChecker =
            object : DiffUtil.ItemCallback<Services>() {
                override fun areItemsTheSame(
                    oldItem: Services,
                    newItem: Services,
                ): Boolean = oldItem.title == newItem.title

                override fun areContentsTheSame(
                    oldItem: Services,
                    newItem: Services,
                ): Boolean = oldItem == newItem
            }
    }
}