package com.ar.ui_practice.ui_component.indicator

import android.view.Gravity
import android.widget.LinearLayout
import androidx.annotation.AnimatorRes
import androidx.annotation.DrawableRes
import com.ar.ui_practice.R

class Config private constructor(
    var width: Int = -1,
    var height: Int = -1,
    var margin: Int = -1,
    @AnimatorRes var animatorResId: Int = R.animator.scale_with_alpha,
    @AnimatorRes var animatorReverseResId: Int = 0,
    @DrawableRes var backgroundResId: Int = R.drawable.ic_indicator,
    @DrawableRes var unselectedBackgroundId: Int = 0,
    var orientation: Int = LinearLayout.HORIZONTAL,
    var gravity: Int = Gravity.CENTER
) {

    class Builder {
        private val config = Config()

        fun width(width: Int) = apply { config.width = width }
        fun height(height: Int) = apply { config.height = height }
        fun margin(margin: Int) = apply { config.margin = margin }
        fun animator(@AnimatorRes animatorResId: Int) = apply { config.animatorResId = animatorResId }
        fun animatorReverse(@AnimatorRes animatorReverseResId: Int) = apply { config.animatorReverseResId = animatorReverseResId }
        fun drawable(@DrawableRes backgroundResId: Int) = apply { config.backgroundResId = backgroundResId }
        fun drawableUnselected(@DrawableRes unselectedBackgroundId: Int) = apply { config.unselectedBackgroundId = unselectedBackgroundId }
        fun orientation(orientation: Int) = apply { config.orientation = orientation }
        fun gravity(gravity: Int) = apply { config.gravity = gravity }

        fun build(): Config = config
    }
}
