package com.iadvize.conversation.sdk.demo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.activities.ProductActivity
import com.iadvize.conversation.sdk.demo.adapters.OnItemClickListener
import com.iadvize.conversation.sdk.demo.adapters.ProductAdapter
import com.iadvize.conversation.sdk.demo.databinding.CatalogFragmentBinding
import com.iadvize.conversation.sdk.demo.models.Catalog
import com.iadvize.conversation.sdk.demo.models.Product
import com.iadvize.conversation.sdk.demo.utils.ItemOffsetDecoration

/**
 * Created by Yann Coupé on 20/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class CatalogFragment : Fragment(), OnItemClickListener {
    private lateinit var binding: CatalogFragmentBinding
    private val adapter = ProductAdapter(Catalog.products, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.catalog_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.catalogRecyclerview.setHasFixedSize(true)
        binding.catalogRecyclerview.layoutManager = GridLayoutManager(context, 2)
        binding.catalogRecyclerview.addItemDecoration(
            ItemOffsetDecoration(
                2,
                resources.getDimensionPixelSize(R.dimen.common_margin)
            )
        )
        binding.catalogRecyclerview.adapter = adapter
    }

    override fun onItemClick(item: Product) {
        val intent = Intent(context, ProductActivity::class.java)
        intent.putExtra(ProductActivity.EXTRA_PRODUCT, item)
        startActivity(intent)
    }
}