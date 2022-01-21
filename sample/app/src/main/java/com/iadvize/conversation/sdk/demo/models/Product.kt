package com.iadvize.conversation.sdk.demo.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Yann Coupé on 20/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
@Parcelize
data class Product(
    var name: String,
    var description: String,
    var pictureResId: Int,
    var price: Int
) : Parcelable
