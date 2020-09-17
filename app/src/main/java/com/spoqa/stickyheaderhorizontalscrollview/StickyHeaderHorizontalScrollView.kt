package com.spoqa.stickyheaderhorizontalscrollview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.HorizontalScrollView

open class StickyHeaderHorizontalScrollView : HorizontalScrollView, ViewTreeObserver.OnGlobalLayoutListener {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    )

    // When scrolling fast, it looks like a bounce effect,
    // so set up padding directly in the child view.

    // List of sticky column - parent view
    private var stickyParentViews = ArrayList<View>()
    // List of sticky column - child view
    private var stickyChildViews = ArrayList<View>()

    // If the width of the sticky view is adjusted according to scrolling,
    // set the stickyViewWidth and stickyViewMinWidth.

    // Initial width value of sticky column
    private var initWidthOfStickyColumn = 0

    // Minimum width value of sticky column
    var minWidthOfStickyColumn: Int? = null

    private var stickyColumnFixedX = 0
        get() {
            minWidthOfStickyColumn?.let {
                field = initWidthOfStickyColumn - it
            }
            return if (field < 0) 0 else field
        }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (l < stickyColumnFixedX) {
            stickyChildViews.map { textView ->
                textView.setPadding(l, textView.paddingTop, textView.paddingRight, textView.paddingBottom)
            }
            stickyParentViews.map { view -> view.translationX = 0f }
        } else {
            stickyChildViews.map { textView ->
                textView.setPadding(stickyColumnFixedX, textView.paddingTop, textView.paddingRight, textView.paddingBottom)
            }
            stickyParentViews.map { view -> view.translationX = (l - stickyColumnFixedX).toFloat() }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {}

    override fun getLeftFadingEdgeStrength(): Float {
        return 0.0f
    }
}
