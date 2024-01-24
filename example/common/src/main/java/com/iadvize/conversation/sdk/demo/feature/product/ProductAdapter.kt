package com.iadvize.conversation.sdk.demo.feature.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iadvize.conversation.sdk.demo.common.databinding.ProductListItemViewBinding

class ProductAdapter(
    private val items: List<Product>,
    private val itemClickListener: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ProductListItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.name.text = item.name
        holder.binding.picture.setImageResource(item.pictureResId)
        holder.binding.price.text = item.newPrice
        holder.binding.root.setOnClickListener { itemClickListener.invoke(item) }
    }

    class ViewHolder(val binding: ProductListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root)
}
