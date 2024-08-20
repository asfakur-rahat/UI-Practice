package com.ar.ui_practice.ui_component.indicator

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class Indicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : BaseIndicator(context, attrs, defStyleAttr, defStyleRes) {

    private var recyclerView: RecyclerView? = null
    private var snapHelper: SnapHelper? = null

    fun attachToRecyclerView(recyclerView: RecyclerView, snapHelper: SnapHelper) {
        this.recyclerView = recyclerView
        this.snapHelper = snapHelper
        mLastPosition = -1
        createIndicators()
        recyclerView.removeOnScrollListener(internalOnScrollListener)
        recyclerView.addOnScrollListener(internalOnScrollListener)
    }

    private fun createIndicators() {
        val adapter = recyclerView?.adapter
        val count = adapter?.itemCount ?: 0
        createIndicators(count, getSnapPosition(recyclerView?.layoutManager))
    }

    fun getSnapPosition(layoutManager: RecyclerView.LayoutManager?): Int {
        if (layoutManager == null) return RecyclerView.NO_POSITION
        val snapView = snapHelper?.findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }

    private val internalOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val position = getSnapPosition(recyclerView.layoutManager)
            if (position == RecyclerView.NO_POSITION) return
            animatePageSelected(position)
        }
    }

    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            val recyclerView = recyclerView ?: return
            val adapter = recyclerView.adapter
            val newCount = adapter?.itemCount ?: 0
            val currentCount = childCount
            mLastPosition = when {
                newCount == currentCount -> mLastPosition
                mLastPosition < newCount -> getSnapPosition(recyclerView.layoutManager)
                else -> RecyclerView.NO_POSITION
            }
            createIndicators()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            onChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            super.onItemRangeChanged(positionStart, itemCount, payload)
            onChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            onChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            onChanged()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            onChanged()
        }
    }

    fun getAdapterDataObserver(): RecyclerView.AdapterDataObserver = adapterDataObserver
}
