package com.ar.ui_practice.ui_component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.ar.ui_practice.R

class SlideButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var initialX: Float = 0f
    private var currentX: Float = 0f
    private var isSliding: Boolean = false
    var onSlideCompleteListener: (() -> Unit)? = null

    private val buttonDrawable = ContextCompat.getDrawable(context, R.drawable.ic_slide_btn)!!

    private val textPaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.darker_gray)
        textSize = 48f
        isAntiAlias = true
    }
    private val text = "Swipe to confirm"

    // Fixed button dimensions in dp
    private val buttonWidthDp = 50
    private val buttonHeightDp = 40

    private val buttonWidthPx: Float
    private val buttonHeightPx: Float

    // Slide bounds
    private val slidePaddingPx: Float = dpToPx(5) // Padding from edges

    init {
        // Convert dp to pixels
        buttonWidthPx = dpToPx(buttonWidthDp)
        buttonHeightPx = dpToPx(buttonHeightDp)
    }

    private fun dpToPx(dp: Int): Float {
        val density = context.resources.displayMetrics.density
        return dp * density
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the text on top of the button
        val textWidth = textPaint.measureText(text)
        val textX = (width - textWidth) / 2f
        val textY = (height - textPaint.descent() - textPaint.ascent()) / 2f
        canvas.drawText(text, textX, textY, textPaint)

        // Draw the slideable button
        val buttonLeft = currentX.toInt() + dpToPx(5).toInt()
        val buttonRight = (currentX + buttonWidthPx).toInt()
        val buttonTop = dpToPx(5).toInt()
        val buttonBottom = buttonHeightPx.toInt()
        buttonDrawable.setBounds(buttonLeft, buttonTop, buttonRight, buttonBottom)
        buttonDrawable.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = event.x
                if (initialX < buttonHeightPx) {
                    isSliding = true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (isSliding) {
                    currentX = event.x - initialX

                    // Apply slide bounds
                    val minX = slidePaddingPx
                    val maxX = width - buttonWidthPx - slidePaddingPx
                    if (currentX < minX) currentX = minX
                    if (currentX > maxX) currentX = maxX

                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (isSliding) {
                    if (currentX >= width - buttonWidthPx - slidePaddingPx) {
                        onSlideCompleteListener?.invoke()
                    }
                    currentX = 0f
                    isSliding = false
                    invalidate()
                }
            }
        }
        return true
    }
}
