package com.iadvize.conversation.sdk.demo.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.models.Product

/**
 * Created by Yann Coupé on 21/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val cartPicture = view.findViewById<ImageView>(R.id.cart_item_picture)
    private val cartName = view.findViewById<TextView>(R.id.cart_item_name)
    private val cartPrice = view.findViewById<TextView>(R.id.cart_item_price)
    private val cartQuantity = view.findViewById<TextView>(R.id.cart_item_quantity)

    fun bind(product: Product, quantity: Int) {
        cartPicture.setImageResource(product.pictureResId)
        cartName.text = product.name
        cartPrice.text = "$${product.price * quantity}"

        cartQuantity.text = quantity.toString()
        cartQuantity.visibility = if (quantity > 1) View.VISIBLE else View.GONE
    }
}