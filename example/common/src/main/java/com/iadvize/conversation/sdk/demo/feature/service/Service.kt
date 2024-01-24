package com.iadvize.conversation.sdk.demo.feature.service

import android.os.Parcelable
import com.iadvize.conversation.sdk.demo.common.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Service(
    val iconResId: Int,
    val name: String,
    val description: String
) : Parcelable

val services: List<Service> = listOf(
    Service(
        R.drawable.ic_shipping,
        "Same day delivery",
        "If you order by 6pm."
    ),
    Service(
        R.drawable.ic_collect,
        "Click&Collect",
        "Pay online now or pay at the pick-up."
    ),
    Service(
        R.drawable.ic_return,
        "Free returns",
        "To any Smart store within 30 days."
    )
)
