package com.iadvize.conversation.sdk.demo.feature.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iadvize.conversation.sdk.demo.common.databinding.ServiceListItemViewBinding

class ServiceAdapter : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {
    override fun getItemCount(): Int = services.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ServiceListItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = services[position]
        holder.binding.name.text = item.name
        holder.binding.subtitle.text = item.description
        holder.binding.icon.setImageResource(item.iconResId)
    }

    class ViewHolder(val binding: ServiceListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root)
}
