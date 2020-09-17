package com.spoqa.stickyscrollview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

open class StickyColumnHorizontalScrollView : HorizontalScrollView, ViewTreeObserver.OnGlobalLayoutListener {

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
     * Set the stickyHeaderColumn variable so that it can be covered even
     * when the header is a layout that is not included in the recycleView.
     *
     * If the header is in the RecyclerView, you do not need to assign it.
     */
    // header view to make sticky
    var stickyHeaderColumn: View? = null
        set(value) {
            field = value
            field?.translationZ = 1f
        }

    // RecyclerView with a column to make sticky
    var recyclerView: RecyclerView? = null

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

    /**
     * Assign variables in onGlobalLayout to get the views you need right,
     * and prevent them from duplicate assignment.
     */
    override fun onGlobalLayout() {
        stickyHeaderColumn?.let { headerView ->
            if (headerView.width != 0 && initWidthOfStickyColumn == 0) {
                initWidthOfStickyColumn = headerView.width
                stickyParentViews.add(headerView)
                (headerView as ViewGroup).children.forEach { childView ->
                    if (childView is TextView) {
                        stickyChildViews.add(childView)
                    }
                }
            }
        }
        val tableRowsSize = stickyParentViews.size - if (stickyHeaderColumn != null) 1 else 0
        if (tableRowsSize >= recyclerView?.childCount ?: 0) return
        recyclerView?.let { listView ->
            for (i in 0 until listView.childCount) {
                val columnView = (listView.getChildAt(i) as ViewGroup).getChildAt(0)
                columnView.translationZ = 1f
                stickyParentViews.add(columnView)
                (columnView as ViewGroup).children.forEach { childView ->
                    if (childView is TextView) {
                        stickyChildViews.add(childView)
                    }
                }
            }
        }
    }

    override fun getLeftFadingEdgeStrength(): Float {
        return 0.0f
    }
}