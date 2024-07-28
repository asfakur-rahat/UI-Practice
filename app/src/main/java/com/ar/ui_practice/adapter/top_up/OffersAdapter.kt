package com.ar.ui_practice.adapter.top_up

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ar.ui_practice.R
import com.ar.ui_practice.R.color.activeColor
import com.ar.ui_practice.databinding.ItemOptionsBinding

class OffersAdapter(
    private val onClick: (Int) -> Unit
): ListAdapter<String, OffersAdapter.OffersViewHolder>(DiffChecker) {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class OffersViewHolder(
        private val binding: ItemOptionsBinding,
        private val onClick: (Int) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                onClick(adapterPosition)
            }
        }
        fun bind(data: String, isSelected: Boolean){
            itemView.isSelected = isSelected
            binding.optionName.text = data
            if (isSelected){
                binding.apply {
                    optionName.setTextColor(Color.parseColor("#3DAE72"))
                    underline.visibility = View.VISIBLE
                }
            }else{
                binding.underline.visibility = View.GONE
                binding.apply {
                    optionName.setTextColor(Color.parseColor("#B3000000"))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersViewHolder {
        val binding = ItemOptionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OffersViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {
        holder.bind(getItem(position), position == selectedPosition)
    }

    fun updateSelectedPosition(newPosition: Int) {
        val previousPosition = selectedPosition
        selectedPosition = newPosition
        notifyItemChanged(previousPosition)
        notifyItemChanged(newPosition)
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