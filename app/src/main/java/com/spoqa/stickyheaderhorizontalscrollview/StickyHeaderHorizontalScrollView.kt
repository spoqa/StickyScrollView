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

    /**
     * If you adjust the padding of the parent view(Layout),
     * it looks like a bounce effect when scrolling fast.
     * So adjust the padding directly in the child view(TextView).
     * To do this, set the variables for the parent view(layout) and the child view(TextView) separately.
     */
    // List of sticky column - parent view
    private var stickyParentViews = ArrayList<View>()
    // List of sticky column - child view
    private var stickyChildViews = ArrayList<View>()

    // Initial width value of sticky column
    private var initWidthOfStickyColumn = 0

    /**
     * If the width of the sticky column is adjusted according to scrolling,
     * set the minWidthOfStickyColumn.
     */
    // Minimum width value of sticky column
    var minWidthOfStickyColumn: Int? = null

    // The x position that starts to fixed during scrolling
    private var stickyColumnFixedX = 0
        get() {
            minWidthOfStickyColumn?.let {
                field = initWidthOfStickyColumn - it
            }
            return if (field < 0) 0 else field
        }

    /**
     * If the scroll X value is less than stickyViewFixedX,
     * adjust the left padding of the child views to the scrolled x value,
     * adjust the translationX of parent views to zero.
     *
     * If the scroll X value is same as stickyViewFixedX or higher,
     * adjust the left padding of the child views to stickyViewFixedX,
     * adjust the translationX of the parent views to value of the scrolled x value minus stickyViewFixedX.
     *
     * If you do this, without actually adjusting the width of the sticky column,
     * the sticky column will be fixed to the left, and as you scroll,
     * the sticky column's width will appear to be adjusted.
     */
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
