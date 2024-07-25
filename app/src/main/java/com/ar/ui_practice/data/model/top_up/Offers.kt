package com.ar.ui_practice.data.model.top_up

import kotlinx.serialization.Serializable

@Serializable
data class Offers(
    val category: String,
    val offerList: List<Offer> = emptyList()
)
