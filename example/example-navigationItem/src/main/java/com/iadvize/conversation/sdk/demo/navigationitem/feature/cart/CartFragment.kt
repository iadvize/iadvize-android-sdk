package com.iadvize.conversation.sdk.demo.navigationitem.feature.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iadvize.conversation.sdk.demo.navigationitem.databinding.CartFragmentBinding

class CartFragment : Fragment() {
    private var binding: CartFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CartFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }
}
