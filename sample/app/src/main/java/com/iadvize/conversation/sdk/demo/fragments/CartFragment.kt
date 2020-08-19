package com.iadvize.conversation.sdk.demo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.iadvize.conversation.sdk.model.Transaction
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.adapters.CartAdapter
import com.iadvize.conversation.sdk.demo.models.Cart
import com.iadvize.conversation.sdk.transaction.IAdvizeTransactionManager
import com.iadvize.conversation.sdk.type.Currency
import kotlinx.android.synthetic.main.fragment_cart.*
import java.util.* // ktlint-disable no-wildcard-imports

/**
 * Created by Yann Coupé on 21/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class CartFragment : Fragment() {

    private val adapter = CartAdapter(Cart.cart.entries)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cart_recyclerview.setHasFixedSize(true)
        cart_recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cart_recyclerview.adapter = adapter

        cart_purchase.setOnClickListener {
            Snackbar.make(view, "Transaction successful!", Snackbar.LENGTH_LONG).show()

            IAdvizeTransactionManager.register(Transaction("id-transaction", Date(), Cart.getAmount().toDouble(), Currency.EUR))

            clear()
        }

        cart_clear.setOnClickListener {
            clear()
        }
    }

    private fun clear() {
        Cart.cart.clear()

        adapter.refresh()

        cart_purchase.visibility = View.GONE
        cart_clear.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        adapter.refresh()

        if (Cart.cart.isEmpty()) {
            cart_purchase.visibility = View.GONE
            cart_clear.visibility = View.GONE
        } else {
            cart_purchase.visibility = View.VISIBLE
            cart_clear.visibility = View.VISIBLE
        }
    }
}