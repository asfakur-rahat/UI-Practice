package com.ar.ui_practice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ar.ui_practice.data.model.ShortcutData
import com.ar.ui_practice.databinding.ShortCutSelectionItemBinding

class ShortCutSelectorAdapter(
    private val onClick: (ShortcutData) -> Unit,
) : ListAdapter<ShortcutData, ShortCutSelectorAdapter.ShortCutSelectorViewHolder>(DiffChecker) {

    class ShortCutSelectorViewHolder(
        private val binding: ShortCutSelectionItemBinding,
        private val onClick: (ShortcutData) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ShortcutData) {
            binding.ivShortcut.setImageResource(data.icon)
            binding.tvShortcut.text = data.title
            binding.root.setOnClickListener {
                onClick(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShortCutSelectorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ShortCutSelectionItemBinding.inflate(layoutInflater, parent, false)
        return ShortCutSelectorViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(
        holder: ShortCutSelectorViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        val DiffChecker =
            object : DiffUtil.ItemCallback<ShortcutData>() {
                override fun areItemsTheSame(
                    oldItem: ShortcutData,
                    newItem: ShortcutData,
                ): Boolean = oldItem.title == newItem.title

                override fun areContentsTheSame(
                    oldItem: ShortcutData,
                    newItem: ShortcutData,
                ): Boolean = oldItem == newItem
            }
    }
}
