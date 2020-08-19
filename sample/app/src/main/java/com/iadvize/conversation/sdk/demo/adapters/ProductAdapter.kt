package com.iadvize.conversation.sdk.demo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.models.Product
import com.iadvize.conversation.sdk.demo.viewholders.ProductViewHolder

/**
 * Created by Yann Coupé on 20/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class ProductAdapter(val products: ArrayList<Product>, val listener: OnItemClickListener? = null) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position], listener)
    }

    override fun onViewRecycled(holder: ProductViewHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    override fun getItemCount(): Int = products.size
}

interface OnItemClickListener {
    fun onItemClick(item: Product)
}
