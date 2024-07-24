package com.ar.ui_practice.data.model.top_up

data class Offer(
    val expire: String,
    val price: String,
    val bundle: List<OfferItem>
)
