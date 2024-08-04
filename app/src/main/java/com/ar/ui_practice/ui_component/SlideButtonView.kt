package com.ar.ui_practice.ui_component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
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
    private var isSlideComplete: Boolean = false
    var onSlideDoneListener: (() -> Unit)? = null

    private lateinit var buttonDrawable: Drawable

    private val textPaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.darker_gray)
        textSize = 48f
        isAntiAlias = true
    }

    private var initialText: String? = null
    private var doneText: String? = null

    private var text = ""

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

        // Parse custom attributes
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SlideButtonView)
            initialText = typedArray.getString(R.styleable.SlideButtonView_initialText)
            doneText = typedArray.getString(R.styleable.SlideButtonView_doneText)
            text = initialText ?: ""  // Set initial text

            // Load the drawable from XML attribute
            val drawableResId = typedArray.getResourceId(R.styleable.SlideButtonView_sliderIcon, R.drawable.ic_slide_btn)
            buttonDrawable = ContextCompat.getDrawable(context, drawableResId) ?: throw IllegalArgumentException("Invalid drawable resource ID")

            typedArray.recycle()
        } ?: run {
            // Default drawable if no attributes provided
            buttonDrawable = ContextCompat.getDrawable(context, R.drawable.ic_slide_btn)!!
        }
    }

    private fun dpToPx(dp: Int): Float {
        val density = context.resources.displayMetrics.density
        return dp * density
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the text
        val textWidth = textPaint.measureText(text)
        val textX = (width - textWidth) / 2f
        val textY = (height - textPaint.descent() - textPaint.ascent()) / 2f
        canvas.drawText(text, textX, textY, textPaint)

        // Draw the slide able button
        val buttonLeft = currentX.toInt() + slidePaddingPx.toInt()
        val buttonRight = (currentX + buttonWidthPx).toInt()
        val buttonTop = slidePaddingPx.toInt()
        val buttonBottom = buttonHeightPx.toInt()
        buttonDrawable.setBounds(buttonLeft, buttonTop, buttonRight, buttonBottom)
        buttonDrawable.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isSlideComplete) {
                    initialX = event.x
                    if (initialX >= 0 && initialX <= buttonWidthPx + slidePaddingPx) {
                        isSliding = true
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (isSliding) {
                    val deltaX = event.x - initialX
                    currentX += deltaX

                    // Apply slide bounds
                    val minX = 0f
                    val maxX = width - buttonWidthPx - slidePaddingPx
                    if (currentX < minX) currentX = minX
                    if (currentX > maxX) currentX = maxX

                    // Update the text dynamically based on slider position
                    text = if (currentX >= maxX) doneText ?: "" else initialText ?: ""
                    initialX = event.x // Update initialX to the current touch position for continuous sliding
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (isSliding) {
                    if (currentX >= width - buttonWidthPx - slidePaddingPx) {
                        isSlideComplete = true
                        currentX = width - buttonWidthPx - slidePaddingPx // Keep the button at the end
                    } else {
                        currentX = 0f // Reset if not fully slid
                    }
                    isSliding = false
                    if (isSlideComplete) {
                        onSlideDoneListener?.invoke() // Notify slide done
                    }
                    invalidate()
                }
            }
        }
        return true
    }

    fun setInitialText(newText: String) {
        initialText = newText
        if (!isSliding) {
            text = initialText!! // Immediately update the text if not sliding
        }
        invalidate()
    }

    fun setDoneText(newText: String) {
        doneText = newText
        if (isSlideComplete) {
            text = doneText!! // Immediately update the text if slide is complete
        }
        invalidate()
    }

    fun resetSlider() {
        isSlideComplete = false
        text = initialText!!
        currentX = 0f
        invalidate()
    }

    fun setIcon(drawableResId: Int) {
        buttonDrawable = ContextCompat.getDrawable(context, drawableResId) ?: throw IllegalArgumentException("Invalid drawable resource ID")
        invalidate()
    }
}
