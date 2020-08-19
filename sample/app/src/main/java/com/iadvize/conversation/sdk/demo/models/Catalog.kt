package com.iadvize.conversation.sdk.demo.models

import android.content.Context
import com.iadvize.conversation.sdk.demo.R

/**
 * Created by Yann Coupé on 20/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
object Catalog {

    val products = arrayListOf<Product>()

    fun construct(context: Context) {
        val pictures = context.resources.obtainTypedArray(R.array.catalog_pictures)
        val titles = context.resources.obtainTypedArray(R.array.catalog_names)
        val descriptions = context.resources.obtainTypedArray(R.array.catalog_descriptions)
        val prices = context.resources.obtainTypedArray(R.array.catalog_prices)

        val size = pictures.length()
        for (i in 0 until size) {
            products.add(Product(titles.getString(i) ?: "", descriptions.getString(i)
                    ?: "", pictures.getResourceId(i, -1), prices.getInteger(i, 0)))
        }

        pictures.recycle()
        titles.recycle()
        descriptions.recycle()
        prices.recycle()
    }
}