@file:JvmName("Utils")

package com.ar.ui_practice.ui_component.carousel.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import coil.load
import com.ar.ui_practice.ui_component.carousel.model.CarouselItem

/**
 * This method converts device specific pixels to density independent pixels.
 */
fun Int.pxToDp(context: Context): Int {
    return (this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 */
fun Int.dpToPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics,
    ).toInt()
}

/**
 * This method converts sp unit to equivalent pixels, depending on device density.
 */
fun Int.spToPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        context.resources.displayMetrics,
    ).toInt()
}

/**
 * Get current snap item position of a recyclerView.
 *
 * @param layoutManager Target recyclerView
 * @return Position of the item or RecyclerView.NO_POSITION (-1)
 */
fun SnapHelper.getSnapPosition(layoutManager: RecyclerView.LayoutManager?): Int {
    if (layoutManager == null) {
        return RecyclerView.NO_POSITION
    }
    val snapView: View = this.findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}

/**
 * Set image to the carouse image view.
 *
 * @param item The carousel item.
 */
fun ImageView.setImage(item: CarouselItem) {
    this.setImage(
        item = item,
        placeholderDrawable = null,
        placeholderDrawableResourceId = null,
    )
}

/**
 * Set image to the carouse image view.
 *
 * @param item The carousel item.
 * @param placeholderDrawableResourceId The id of the resource to use as a placeholder.
 */
fun ImageView.setImage(
    item: CarouselItem,
    @DrawableRes placeholderDrawableResourceId: Int,
) {
    this.setImage(
        item = item,
        placeholderDrawable = null,
        placeholderDrawableResourceId = placeholderDrawableResourceId,
    )
}

/**
 * Set image to the carouse image view.
 *
 * @param item The carousel item.
 * @param placeholderDrawable The drawable to display as a placeholder.
 */
fun ImageView.setImage(
    item: CarouselItem,
    placeholderDrawable: Drawable? = null,
) {
    this.setImage(
        item = item,
        placeholderDrawable = placeholderDrawable,
        placeholderDrawableResourceId = null,
    )
}

/**
 * Set image to the carouse image view.
 *
 * @param item The carousel item.
 * @param placeholderDrawable The drawable to display as a placeholder.
 * @param placeholderDrawableResourceId The id of the resource to use as a placeholder.
 */
private fun ImageView.setImage(
    item: CarouselItem,
    placeholderDrawable: Drawable? = null,
    @DrawableRes placeholderDrawableResourceId: Int? = null,
) {
    when {
        item.imageUrl != null && item.headers == null -> {
            this.load(item.imageUrl) {
                if (placeholderDrawable!=null){
                    placeholder(placeholderDrawable)
                }else if (placeholderDrawableResourceId!=null){
                    placeholder(placeholderDrawableResourceId)
                }
            }
        }

        item.headers != null -> {
            //glide.load(GlideUrl(item.imageUrl.toString()) { item.headers })
            this.load(item.imageUrl) {
                if (placeholderDrawable!=null){
                    placeholder(placeholderDrawable)
                }else if (placeholderDrawableResourceId!=null){
                    placeholder(placeholderDrawableResourceId)
                }
            }
        }

        else -> {
            this.load(item.imageDrawable) {
                if (placeholderDrawable!=null){
                    placeholder(placeholderDrawable)
                }else if (placeholderDrawableResourceId!=null){
                    placeholder(placeholderDrawableResourceId)
                }
            }
        }
    }
}
