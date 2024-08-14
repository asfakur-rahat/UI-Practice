package com.ar.ui_practice.ui_component.indicator

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

class SnackbarBehavior @JvmOverloads constructor(
    context: Context? = null,
    attrs: AttributeSet? = null
) : CoordinatorLayout.Behavior<BaseIndicator>(context, attrs) {

    @SuppressLint("RestrictedApi")
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: BaseIndicator,
        dependency: View
    ): Boolean {
        return dependency is Snackbar.SnackbarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: BaseIndicator,
        dependency: View
    ): Boolean {
        val translationY = getTranslationYForSnackbar(parent, child)
        child.translationY = translationY
        return true
    }

    @SuppressLint("RestrictedApi")
    private fun getTranslationYForSnackbar(parent: CoordinatorLayout, ci: BaseIndicator): Float {
        var minOffset = 0f
        val dependencies = parent.getDependencies(ci)
        for (view in dependencies) {
            if (view is Snackbar.SnackbarLayout && parent.doViewsOverlap(ci, view)) {
                minOffset = minOf(minOffset, view.translationY - view.height)
            }
        }
        return minOffset
    }
}
