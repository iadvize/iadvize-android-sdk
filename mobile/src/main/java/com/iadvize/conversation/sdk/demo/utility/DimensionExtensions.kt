package com.iadvize.conversation.sdk.demo.utility

import android.content.Context
import android.util.TypedValue
import kotlin.math.roundToInt

fun Float.dpToPx(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    context.resources.displayMetrics
).roundToInt()

fun Int.dpToPx(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    context.resources.displayMetrics
).roundToInt()

