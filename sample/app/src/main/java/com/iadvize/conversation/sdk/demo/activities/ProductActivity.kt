package com.iadvize.conversation.sdk.demo.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.models.Cart
import com.iadvize.conversation.sdk.demo.models.Product
import kotlinx.android.synthetic.main.activity_product.*

/**
 * Created by Yann Coupé on 21/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class ProductActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val product = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)
                ?: throw NullPointerException("The product must not be null")
        product_toolbar.title = product.name
        setSupportActionBar(product_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        product_picture.setImageResource(product.pictureResId)
        product_price.text = "$${product.price}"
        product_description.text = product.description
        product_add.setOnClickListener {
            Cart.addProduct(product)
            Snackbar.make(findViewById(android.R.id.content), "Added to cart!", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}