package com.ar.ui_practice.data.model.top_up

import kotlinx.serialization.Serializable


@Serializable
data class Offer(
    val expire: String,
    val price: String,
    val bundle: List<OfferItem>
)
