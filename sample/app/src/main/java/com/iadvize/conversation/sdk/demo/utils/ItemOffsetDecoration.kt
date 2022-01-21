package com.iadvize.conversation.sdk.demo.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Yann Coupé on 21/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class ItemOffsetDecoration(private val spanCount: Int, private val spacing: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.left = spacing - column * spacing / spanCount
        outRect.right = (column + 1) * spacing / spanCount

        outRect.top = if (position < spanCount) spacing else 0
        outRect.bottom = spacing
    }
}