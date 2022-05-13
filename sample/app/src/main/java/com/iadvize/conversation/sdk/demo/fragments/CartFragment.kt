package com.iadvize.conversation.sdk.demo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.adapters.CartAdapter
import com.iadvize.conversation.sdk.demo.databinding.CartFragmentBinding
import com.iadvize.conversation.sdk.demo.models.Cart
import com.iadvize.conversation.sdk.feature.transaction.Transaction
import com.iadvize.conversation.sdk.type.Currency
import java.util.*

/**
 * Created by Yann Coupé on 21/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class CartFragment : Fragment() {
    private lateinit var binding: CartFragmentBinding
    private val adapter = CartAdapter(Cart.cart.entries)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.cart_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cartRecyclerview.setHasFixedSize(true)
        binding.cartRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.cartRecyclerview.adapter = adapter

        binding.cartPurchase.setOnClickListener {
            Snackbar.make(view, "Transaction successful!", Snackbar.LENGTH_LONG).show()

            IAdvizeSDK.transactionController.register(
                Transaction(
                    "id-transaction",
                    Date(),
                    Cart.getAmount().toDouble(),
                    Currency.EUR
                )
            )

            clear()
        }

        binding.cartClear.setOnClickListener {
            clear()
        }
    }

    private fun clear() {
        Cart.cart.clear()

        adapter.refresh()

        binding.cartPurchase.visibility = View.GONE
        binding.cartClear.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        adapter.refresh()

        if (Cart.cart.isEmpty()) {
            binding.cartPurchase.visibility = View.GONE
            binding.cartClear.visibility = View.GONE
        } else {
            binding.cartPurchase.visibility = View.VISIBLE
            binding.cartClear.visibility = View.VISIBLE
        }
    }
}