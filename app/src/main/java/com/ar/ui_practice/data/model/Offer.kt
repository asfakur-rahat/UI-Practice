package com.ar.ui_practice.data.model

import com.ar.ui_practice.R.drawable.*
import kotlinx.serialization.Serializable

@Serializable
data class Offer(
    val internet: String? = null,
    val min: String? = null,
    val sms: String? = null,
    val iconInternet: Int = ic_wifi,
    val iconMin: Int = ic_minute,
    val iconSMS: Int = ic_sms,
    val limit : String,
    val price: String
)
