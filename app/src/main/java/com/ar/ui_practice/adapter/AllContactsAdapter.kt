package com.ar.ui_practice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ar.ui_practice.R
import com.ar.ui_practice.data.model.Contacts
import com.ar.ui_practice.databinding.ContactItemBinding


class AllContactsAdapter(
    private val onClick: (Contacts) -> Unit,
) : ListAdapter<Contacts, AllContactsAdapter.ContactsViewHolder>(DiffChecker) {

    class ContactsViewHolder(
        private val binding: ContactItemBinding,
        private val onClick: (Contacts) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Contacts) {
            binding.contactName.text = data.name
            binding.contactNumber.text = data.number
            binding.avatar.setImageResource(R.drawable.ic_avatar_v2)
            binding.root.setOnClickListener {
                onClick(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ContactsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ContactItemBinding.inflate(layoutInflater, parent, false)
        return ContactsViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(
        holder: ContactsViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        val DiffChecker =
            object : DiffUtil.ItemCallback<Contacts>() {
                override fun areItemsTheSame(
                    oldItem: Contacts,
                    newItem: Contacts,
                ): Boolean = oldItem.name == newItem.name

                override fun areContentsTheSame(
                    oldItem: Contacts,
                    newItem: Contacts,
                ): Boolean = oldItem == newItem
            }
    }
}