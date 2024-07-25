package com.ar.ui_practice.adapter.top_up

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ar.ui_practice.data.model.Offer
import com.ar.ui_practice.databinding.ItemOfferBinding

class OfferAdapter(
    private val onClick: (Offer) -> Unit
): ListAdapter<Offer, OfferAdapter.ViewHolder>(DiffChecker) {
    inner class ViewHolder(
        private val binding: ItemOfferBinding,
        private val onClick: (Offer) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Offer) {
            binding.linearLayout.visibility = View.GONE
            binding.linearLayout2.visibility = View.GONE
            if(item.internet != null){
                binding.linearLayout.visibility = View.VISIBLE
                binding.tvInternet.text = item.internet
            }
            if (item.min != null){
                binding.linearLayout2.visibility = View.VISIBLE
                binding.tvMinute.text = item.min
                binding.icon1.setImageResource(item.iconMin)
            }
            if(item.sms != null){
                binding.linearLayout2.visibility = View.VISIBLE
                binding.tvMinute.text = item.sms
                binding.icon1.setImageResource(item.iconSMS)
            }
            binding.tvDeadline.text = item.limit
            binding.tvPrice.text = item.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding,onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        val DiffChecker = object : DiffUtil.ItemCallback<Offer>() {
            override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
                return oldItem == newItem
            }
        }
    }
}