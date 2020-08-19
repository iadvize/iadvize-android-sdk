package com.iadvize.conversation.sdk.demo.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.adapters.OnItemClickListener
import com.iadvize.conversation.sdk.demo.models.Product

/**
 * Created by Yann Coupé on 20/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val productPicture = view.findViewById<ImageView>(R.id.product_item_picture)
    private val productName = view.findViewById<TextView>(R.id.product_item_name)

    fun bind(product: Product, listener: OnItemClickListener? = null) {
        productName.text = product.name
        productPicture.setImageResource(product.pictureResId)

        itemView.setOnClickListener {
            listener?.onItemClick(product)
        }
    }

    fun recycle() {
        itemView.setOnClickListener(null)
    }
}