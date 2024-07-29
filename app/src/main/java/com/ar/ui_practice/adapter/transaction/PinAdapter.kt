package com.ar.ui_practice.adapter.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ar.ui_practice.databinding.ItemCustomPinBinding

class PinAdapter: ListAdapter<Int, PinAdapter.PinViewHolder>(DiffChecker) {

    class PinViewHolder(binding: ItemCustomPinBinding): ViewHolder(binding.root){
        fun bind(data: Int){

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCustomPinBinding.inflate(layoutInflater, parent, false)
        return PinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        val DiffChecker = object : DiffUtil.ItemCallback<Int>(){
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = newItem ==oldItem
            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean = newItem == oldItem
        }
    }

}