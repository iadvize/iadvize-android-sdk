package com.iadvize.conversation.sdk.demo.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.activities.ProductActivity
import com.iadvize.conversation.sdk.demo.adapters.OnItemClickListener
import com.iadvize.conversation.sdk.demo.adapters.ProductAdapter
import com.iadvize.conversation.sdk.demo.models.Catalog
import com.iadvize.conversation.sdk.demo.models.Product
import com.iadvize.conversation.sdk.demo.utils.ItemOffsetDecoration
import kotlinx.android.synthetic.main.fragment_catalog.*

/**
 * Created by Yann Coupé on 20/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class CatalogFragment : Fragment(), OnItemClickListener {

    private val adapter = ProductAdapter(Catalog.products, this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catalog_recyclerview.setHasFixedSize(true)
        catalog_recyclerview.layoutManager = GridLayoutManager(context, 2)
        catalog_recyclerview.addItemDecoration(ItemOffsetDecoration(2, resources.getDimensionPixelSize(R.dimen.common_margin)))
        catalog_recyclerview.adapter = adapter
    }

    override fun onItemClick(item: Product) {
        val intent = Intent(context, ProductActivity::class.java)
        intent.putExtra(ProductActivity.EXTRA_PRODUCT, item)
        startActivity(intent)
    }
}