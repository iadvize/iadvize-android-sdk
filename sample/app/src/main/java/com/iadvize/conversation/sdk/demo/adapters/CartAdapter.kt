package com.iadvize.conversation.sdk.demo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.models.Cart
import com.iadvize.conversation.sdk.demo.models.Product
import com.iadvize.conversation.sdk.demo.viewholders.CartViewHolder

/**
 * Created by Yann Coupé on 21/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class CartAdapter(var cart: Set<Map.Entry<Product, Int>>) : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val iterator = cart.iterator()
        var i = 0
        while (iterator.hasNext() && i < position) {
            iterator.next()
            i++
        }
        val entry = iterator.next()
        holder.bind(entry.key, entry.value)
    }

    override fun getItemCount(): Int = cart.size

    fun refresh() {
        cart = Cart.cart.entries
        notifyDataSetChanged()
    }
}