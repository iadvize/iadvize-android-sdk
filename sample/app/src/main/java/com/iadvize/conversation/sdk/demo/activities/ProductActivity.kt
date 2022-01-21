package com.iadvize.conversation.sdk.demo.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.databinding.ProductActivityBinding
import com.iadvize.conversation.sdk.demo.models.Cart
import com.iadvize.conversation.sdk.demo.models.Product

/**
 * Created by Yann Coupé on 21/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class ProductActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
    }

    private lateinit var binding: ProductActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.product_activity)

        val product = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)
            ?: throw NullPointerException("The product must not be null")
        binding.productToolbar.title = product.name
        setSupportActionBar(binding.productToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.productPicture.setImageResource(product.pictureResId)
        binding.productPrice.text = "$${product.price}"
        binding.productDescription.text = product.description
        binding.productAdd.setOnClickListener {
            Cart.addProduct(product)
            Snackbar.make(
                findViewById(android.R.id.content),
                "Added to cart!",
                Snackbar.LENGTH_LONG
            ).show()
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