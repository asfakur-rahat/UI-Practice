package com.ar.ui_practice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ar.ui_practice.data.model.MobileOperator
import com.ar.ui_practice.databinding.ItemOperatorBinding


class OperatorSelectorAdapter(
    private val onClick: (MobileOperator) -> Unit
): ListAdapter<MobileOperator, OperatorSelectorAdapter.OperatorViewHolder>(DiffChecker) {
    private var selectedItemPosition: Int = RecyclerView.NO_POSITION
        set(value) {
            val previousItemPosition = field
            field = value
            notifyItemChanged(previousItemPosition)
            notifyItemChanged(value)
        }

    inner class OperatorViewHolder(
        private val binding: ItemOperatorBinding,
        private val onClick: (MobileOperator) -> Unit
    ): ViewHolder(binding.root){
        fun bind(data: MobileOperator){
            binding.icon.setImageResource(data.icon)
            binding.root.setOnClickListener {
                onClick(data)
                selectedItemPosition = adapterPosition
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperatorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemOperatorBinding.inflate(layoutInflater, parent, false)
        return OperatorViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: OperatorViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.isSelected = (selectedItemPosition == position)
    }


    companion object{
        val DiffChecker = object : DiffUtil.ItemCallback<MobileOperator>(){
            override fun areItemsTheSame(
                oldItem: MobileOperator,
                newItem: MobileOperator
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MobileOperator,
                newItem: MobileOperator
            ): Boolean = newItem ==oldItem
        }
    }
}