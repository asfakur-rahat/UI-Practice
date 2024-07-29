package com.ar.ui_practice.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ar.ui_practice.data.model.ShortcutData
import com.ar.ui_practice.databinding.ShortCutItemBinding

class ShortCutAdapter(
    private val onClick: (ShortcutData) -> Unit,
    private val onLongClick: () -> Unit,
) : ListAdapter<ShortcutData, ShortCutAdapter.ShortCutViewHolder>(DiffChecker) {

    class ShortCutViewHolder(
        private val binding: ShortCutItemBinding,
        private val onClick: (ShortcutData) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ShortcutData) {
            binding.ivShortcut.setImageResource(data.icon)
            binding.tvShortcut.text = data.title
            binding.root.setOnClickListener {
                onClick(data)
            }
            if(data.removeIcon){
                binding.ivRemoveShortcut.visibility = View.VISIBLE
            }else{
                binding.ivRemoveShortcut.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShortCutViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ShortCutItemBinding.inflate(layoutInflater, parent, false)
        return ShortCutViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(
        holder: ShortCutViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
        holder.itemView.setOnLongClickListener {
            onLongClick()
            true
        }
    }

    companion object {
        val DiffChecker =
            object : DiffUtil.ItemCallback<ShortcutData>() {
                override fun areItemsTheSame(
                    oldItem: ShortcutData,
                    newItem: ShortcutData,
                ): Boolean = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: ShortcutData,
                    newItem: ShortcutData,
                ): Boolean = oldItem.title == newItem.title
            }
    }
}
