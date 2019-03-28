package com.iadvize.conversation.sdk.demo.models

/**
 * Created by Yann Coupé on 21/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
object Cart {

    val cart = hashMapOf<Product, Int>()

    fun addProduct(product: Product) {
        if (cart.containsKey(product)) {
            cart[product]?.let {
                cart.put(product, it + 1)
            }
        } else {
            cart[product] = 1
        }
    }

    fun getAmount(): Float {
        var total = 0f
        cart.entries.map {
            total += it.value * it.key.price
        }
        return total
    }
}