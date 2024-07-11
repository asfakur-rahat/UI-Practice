package com.ar.ui_practice.data.model

data class ShortcutData(
    val id: Int,
    val icon: Int,
    val title: String,
    var removeIcon: Boolean = false
)
