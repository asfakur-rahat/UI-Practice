package com.ar.ui_practice.data.model.top_up

import kotlinx.serialization.Serializable


@Serializable
data class OfferItem(
    val amount: String,
    val icon: Int
)
