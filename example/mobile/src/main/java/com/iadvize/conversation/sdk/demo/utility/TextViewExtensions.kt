package com.iadvize.conversation.sdk.demo.utility

import android.graphics.Paint
import android.widget.TextView

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.strikethrough() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}
