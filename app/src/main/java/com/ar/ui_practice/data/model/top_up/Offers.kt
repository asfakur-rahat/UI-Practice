package com.ar.ui_practice.data.model.top_up

data class Offers(
    val category: String,
    val offerList: List<Offer> = emptyList()
)
