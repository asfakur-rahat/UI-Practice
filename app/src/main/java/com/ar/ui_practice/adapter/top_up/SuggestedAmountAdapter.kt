package com.ar.ui_practice.adapter.top_up

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ar.ui_practice.databinding.ItemSuggestedAmountBinding

class SuggestedAmountAdapter(
    private val onClick: (String) -> Unit
):ListAdapter<String, SuggestedAmountAdapter.ThisViewHolder>(DiffChecker) {

    inner class ThisViewHolder(
        private val binding: ItemSuggestedAmountBinding,
        private val onClick: (String) -> Unit
    ): ViewHolder(binding.root){
        fun bind(amount: String){
            binding.tvSuggestedAmount.text = amount
            binding.root.setOnClickListener {
                onClick(amount)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThisViewHolder {
        val binding = ItemSuggestedAmountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ThisViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ThisViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        val DiffChecker = object : DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

}