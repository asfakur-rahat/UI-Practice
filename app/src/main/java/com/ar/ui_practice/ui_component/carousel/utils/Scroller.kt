package com.ar.ui_practice.ui_component.carousel.utils

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller

class Scroller(context: Context) : LinearSmoothScroller(context) {

    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
        return .35f
    }

    override fun onStop() {
        super.onStop()
    }
}