package com.example.trans.screens.home_screen

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.trans.utillity.logger.Logger


class BottomMarginItemDecoration(private val margin: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter!!.itemCount

        // Apply bottom margin to the last item
        if (position == itemCount - 1) {
            if ((outRect.bottom == 0 && margin != 0)
                || (margin == 0 && outRect.bottom != 0)
            ) {
                outRect.setEmpty()
                outRect.bottom = 0
                outRect.bottom = margin
            }
        }
    }
}
