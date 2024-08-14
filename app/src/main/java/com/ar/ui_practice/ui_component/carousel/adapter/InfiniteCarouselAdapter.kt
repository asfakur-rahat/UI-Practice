package com.ar.ui_practice.ui_component.carousel.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ar.ui_practice.ui_component.carousel.ImageCarousel
import com.ar.ui_practice.ui_component.carousel.model.CarouselGravity
import com.ar.ui_practice.ui_component.carousel.model.CarouselItem
import com.ar.ui_practice.ui_component.carousel.model.CarouselType

class InfiniteCarouselAdapter(
    recyclerView: RecyclerView,
    carouselType: CarouselType,
    carouselGravity: CarouselGravity,
    autoWidthFixing: Boolean,
    imageScaleType: ImageView.ScaleType,
    imagePlaceholder: Drawable?,
    showCaption: Boolean,
    showSubtitle: Boolean,
) : FiniteCarouselAdapter(
    recyclerView,
    carouselType,
    carouselGravity,
    autoWidthFixing,
    imageScaleType,
    imagePlaceholder,
    showCaption,
    showSubtitle
) {
    override fun getItemCount(): Int {
        return if (dataList.isEmpty()) 0 else Integer.MAX_VALUE
    }

    override fun getItem(position: Int): CarouselItem? {
        return if (position < itemCount) {
            dataList[position % dataList.size]
        } else {
            null
        }
    }

    override fun getRealDataPosition(position: Int): Int {
        if (dataList.size == 0) return ImageCarousel.NO_POSITION
        return position % dataList.size
    }
}