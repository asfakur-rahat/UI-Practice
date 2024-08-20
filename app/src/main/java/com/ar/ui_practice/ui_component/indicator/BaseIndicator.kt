package com.ar.ui_practice.ui_component.indicator

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.Interpolator
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import com.ar.ui_practice.R

open class BaseIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        private const val DEFAULT_INDICATOR_WIDTH = 5
    }

    protected var mIndicatorMargin: Int = -1
    protected var mIndicatorWidth: Int = -1
    protected var mIndicatorHeight: Int = -1

    protected var mIndicatorBackgroundResId: Int = 0
    protected var mIndicatorUnselectedBackgroundResId: Int = 0

    protected var mIndicatorTintColor: ColorStateList? = null
    protected var mIndicatorTintUnselectedColor: ColorStateList? = null

    protected var mAnimatorOut: Animator? = null
    protected var mAnimatorIn: Animator? = null
    protected var mImmediateAnimatorOut: Animator? = null
    protected var mImmediateAnimatorIn: Animator? = null

    protected var mLastPosition: Int = -1

    @Nullable
    private var mIndicatorCreatedListener: IndicatorCreatedListener? = null

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val config = handleTypedArray(context, attrs)
        initialize(config)

        if (isInEditMode) {
            createIndicators(3, 1)
        }
    }

    private fun handleTypedArray(context: Context, attrs: AttributeSet?): Config {
        val config = Config()
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.BaseIndicator)
            config.width = typedArray.getDimensionPixelSize(R.styleable.BaseIndicator_ci_width, -1)
            config.height = typedArray.getDimensionPixelSize(R.styleable.BaseIndicator_ci_height, -1)
            config.margin = typedArray.getDimensionPixelSize(R.styleable.BaseIndicator_ci_margin, -1)
            config.animatorResId = typedArray.getResourceId(R.styleable.BaseIndicator_ci_animator, R.animator.scale_with_alpha)
            config.animatorReverseResId = typedArray.getResourceId(R.styleable.BaseIndicator_ci_animator_reverse, 0)
            config.backgroundResId = typedArray.getResourceId(R.styleable.BaseIndicator_ci_drawable, R.drawable.ic_indicator)
            config.unselectedBackgroundId = typedArray.getResourceId(R.styleable.BaseIndicator_ci_drawable_unselected, config.backgroundResId)
            config.orientation = typedArray.getInt(R.styleable.BaseIndicator_ci_orientation, -1)
            config.gravity = typedArray.getInt(R.styleable.BaseIndicator_ci_gravity, -1)
            typedArray.recycle()
        }
        return config
    }

    fun initialize(config: Config) {
        val miniSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_INDICATOR_WIDTH.toFloat(), resources.displayMetrics).toInt()
        mIndicatorWidth = if (config.width < 0) miniSize else config.width
        mIndicatorHeight = if (config.height < 0) miniSize else config.height
        mIndicatorMargin = if (config.margin < 0) miniSize else config.margin

        mAnimatorOut = createAnimatorOut(config)
        mImmediateAnimatorOut = createAnimatorOut(config).apply {
            duration = 0
        }

        mAnimatorIn = createAnimatorIn(config)
        mImmediateAnimatorIn = createAnimatorIn(config).apply {
            duration = 0
        }

        mIndicatorBackgroundResId = if (config.backgroundResId == 0) R.drawable.ic_indicator else config.backgroundResId
        mIndicatorUnselectedBackgroundResId = if (config.unselectedBackgroundId == 0) config.backgroundResId else config.unselectedBackgroundId

        orientation = if (config.orientation == VERTICAL) VERTICAL else HORIZONTAL
        gravity = if (config.gravity >= 0) config.gravity else Gravity.CENTER
    }

    fun tintIndicator(@ColorInt indicatorColor: Int) {
        tintIndicator(indicatorColor, indicatorColor)
    }

    fun tintIndicator(@ColorInt indicatorColor: Int, @ColorInt unselectedIndicatorColor: Int) {
        mIndicatorTintColor = ColorStateList.valueOf(indicatorColor)
        mIndicatorTintUnselectedColor = ColorStateList.valueOf(unselectedIndicatorColor)
        changeIndicatorBackground()
    }

    fun changeIndicatorResource(@DrawableRes indicatorResId: Int) {
        changeIndicatorResource(indicatorResId, indicatorResId)
    }

    fun changeIndicatorResource(@DrawableRes indicatorResId: Int, @DrawableRes indicatorUnselectedResId: Int) {
        mIndicatorBackgroundResId = indicatorResId
        mIndicatorUnselectedBackgroundResId = indicatorUnselectedResId
        changeIndicatorBackground()
    }

    interface IndicatorCreatedListener {
        /**
         * IndicatorCreatedListener
         *
         * @param view internal indicator view
         * @param position position
         */
        fun onIndicatorCreated(view: View, position: Int)
    }

    fun setIndicatorCreatedListener(@Nullable indicatorCreatedListener: IndicatorCreatedListener?) {
        mIndicatorCreatedListener = indicatorCreatedListener
    }

    protected fun createAnimatorOut(config: Config): Animator {
        return AnimatorInflater.loadAnimator(context, config.animatorResId)
    }

    protected fun createAnimatorIn(config: Config): Animator {
        val animatorIn: Animator
        animatorIn = if (config.animatorReverseResId == 0) {
            AnimatorInflater.loadAnimator(context, config.animatorResId).apply {
                interpolator = ReverseInterpolator()
            }
        } else {
            AnimatorInflater.loadAnimator(context, config.animatorReverseResId)
        }
        return animatorIn
    }

    fun createIndicators(count: Int, currentPosition: Int) {
        mImmediateAnimatorOut?.apply {
            if (isRunning) {
                end()
                cancel()
            }
        }

        mImmediateAnimatorIn?.apply {
            if (isRunning) {
                end()
                cancel()
            }
        }

        // Diff View
        val childViewCount = childCount
        when {
            count < childViewCount -> removeViews(count, childViewCount - count)
            count > childViewCount -> {
                val addCount = count - childViewCount
                val orientation = orientation
                repeat(addCount) {
                    addIndicator(orientation)
                }
            }
        }

        // Bind Style
        for (i in 0 until count) {
            val indicator = getChildAt(i)
            if (currentPosition == i) {
                bindIndicatorBackground(indicator, mIndicatorBackgroundResId, mIndicatorTintColor)
                mImmediateAnimatorOut?.apply {
                    setTarget(indicator)
                    start()
                    end()
                }
            } else {
                bindIndicatorBackground(indicator, mIndicatorUnselectedBackgroundResId, mIndicatorTintUnselectedColor)
                mImmediateAnimatorIn?.apply {
                    setTarget(indicator)
                    start()
                    end()
                }
            }

            mIndicatorCreatedListener?.onIndicatorCreated(indicator, i)
        }

        mLastPosition = currentPosition
    }

    protected fun addIndicator(orientation: Int) {
        val indicator = View(context)
        val params = generateDefaultLayoutParams().apply {
            width = mIndicatorWidth
            height = mIndicatorHeight
            if (orientation == HORIZONTAL) {
                leftMargin = mIndicatorMargin
                rightMargin = mIndicatorMargin
            } else {
                topMargin = mIndicatorMargin
                bottomMargin = mIndicatorMargin
            }
        }
        addView(indicator, params)
    }

    fun animatePageSelected(position: Int) {
        if (mLastPosition == position) return

        mAnimatorIn?.apply {
            if (isRunning) {
                end()
                cancel()
            }
        }

        mAnimatorOut?.apply {
            if (isRunning) {
                end()
                cancel()
            }
        }

        val currentIndicator = if (mLastPosition >= 0) getChildAt(mLastPosition) else null
        currentIndicator?.let {
            bindIndicatorBackground(it, mIndicatorUnselectedBackgroundResId, mIndicatorTintUnselectedColor)
            mAnimatorIn?.apply {
                setTarget(it)
                start()
            }
        }

        val selectedIndicator = getChildAt(position)
        selectedIndicator?.let {
            bindIndicatorBackground(it, mIndicatorBackgroundResId, mIndicatorTintColor)
            mAnimatorOut?.apply {
                setTarget(it)
                start()
            }
        }

        mLastPosition = position
    }

    protected fun changeIndicatorBackground() {
        val count = childCount
        if (count <= 0) return

        for (i in 0 until count) {
            val currentIndicator = getChildAt(i)
            val drawableRes = if (i == mLastPosition) mIndicatorBackgroundResId else mIndicatorUnselectedBackgroundResId
            val tintColor = if (i == mLastPosition) mIndicatorTintColor else mIndicatorTintUnselectedColor
            bindIndicatorBackground(currentIndicator, drawableRes, tintColor)
        }
    }

    private fun bindIndicatorBackground(view: View, @DrawableRes drawableRes: Int, tintColor: ColorStateList?) {
        tintColor?.let {
            val indicatorDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, drawableRes)?.mutate()!!)
            DrawableCompat.setTintList(indicatorDrawable, it)
            ViewCompat.setBackground(view, indicatorDrawable)
        } ?: view.setBackgroundResource(drawableRes)
    }

    protected class ReverseInterpolator : Interpolator {
        override fun getInterpolation(value: Float): Float {
            return Math.abs(1.0f - value)
        }
    }

    class Config {
        var width: Int = -1
        var height: Int = -1
        var margin: Int = -1
        var animatorResId: Int = 0
        var animatorReverseResId: Int = 0
        var backgroundResId: Int = 0
        var unselectedBackgroundId: Int = 0
        var orientation: Int = -1
        var gravity: Int = -1
    }
}
