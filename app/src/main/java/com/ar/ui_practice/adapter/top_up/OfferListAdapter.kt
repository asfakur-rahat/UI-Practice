package com.ar.ui_practice.adapter.top_up

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ar.ui_practice.data.model.top_up.Offer
import com.ar.ui_practice.databinding.ItemOfferBinding
import com.ar.ui_practice.databinding.ItemOptionsBinding
import com.ar.ui_practice.utils.setVisibility

class OfferListAdapter(
    private val onClick: (Offer) -> Unit
): ListAdapter<Offer, OfferListAdapter.OfferListViewHolder>(DiffChecker) {

    inner class OfferListViewHolder(
        private val binding: ItemOfferBinding,
        private val onClick: (Offer) -> Unit
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Offer){
            setLayout(item.bundle.size,item)
            binding.tvPrice.text  = item.price
            binding.tvDeadline.text = item.expire

            binding.root.setOnClickListener {
                onClick(item)
            }
        }

        private fun setLayout(size: Int, item: Offer){

            when(size){
                1 -> {
                    binding.slot2.setVisibility(false)
                    binding.slot3.setVisibility(false)
                    binding.icon1.setImageResource(item.bundle[0].icon)
                    binding.tvSlot1.text = item.bundle[0].amount
                }
                2 -> {
                    binding.slot3.setVisibility(false)

                    binding.icon1.setImageResource(item.bundle[0].icon)
                    binding.tvSlot1.text = item.bundle[0].amount

                    binding.icon2.setImageResource(item.bundle[1].icon)
                    binding.tvSlot2.text = item.bundle[1].amount
                }
                else -> {
                    binding.icon1.setImageResource(item.bundle[0].icon)
                    binding.tvSlot1.text = item.bundle[0].amount

                    binding.icon2.setImageResource(item.bundle[1].icon)
                    binding.tvSlot2.text = item.bundle[1].amount

                    binding.icon3.setImageResource(item.bundle[2].icon)
                    binding.tvSlot3.text = item.bundle[2].amount
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferListViewHolder {
        val binding = ItemOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferListViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: OfferListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        val DiffChecker = object : DiffUtil.ItemCallback<Offer>(){
            override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
                return oldItem == newItem
            }
        }
    }
}