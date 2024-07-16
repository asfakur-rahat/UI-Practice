package com.ar.ui_practice.adapter.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ar.ui_practice.data.model.Service
import com.ar.ui_practice.databinding.ServiceItemBinding

class ServiceAdapter(
    private val onClick: (Service) -> Unit,
) : ListAdapter<Service, ServiceAdapter.ServiceViewHolder>(DiffChecker) {

    class ServiceViewHolder(
        private val binding: ServiceItemBinding,
        private val onClick: (Service) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(service: Service) {
            binding.title.text = service.title
            binding.icon.setImageResource(service.icon)
            binding.root.setOnClickListener {
                onClick(service)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ServiceItemBinding.inflate(layoutInflater, parent, false)
        return ServiceViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DiffChecker =
            object : DiffUtil.ItemCallback<Service>() {
                override fun areItemsTheSame(
                    oldItem: Service,
                    newItem: Service,
                ): Boolean = oldItem.title == newItem.title

                override fun areContentsTheSame(
                    oldItem: Service,
                    newItem: Service,
                ): Boolean = oldItem == newItem
            }
    }
}