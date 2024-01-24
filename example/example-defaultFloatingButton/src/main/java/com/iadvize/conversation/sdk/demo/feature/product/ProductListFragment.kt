package com.iadvize.conversation.sdk.demo.feature.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iadvize.conversation.sdk.demo.common.databinding.ProductListFragmentBinding
import com.iadvize.conversation.sdk.demo.feature.home.HomeFragmentDirections

class ProductListFragment : Fragment() {
    private var binding: ProductListFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductListFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // See all
        binding?.productListView?.seeAll?.visibility = GONE

        // Product List
        binding?.productListView?.recycler?.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            it.adapter = ProductAdapter(products) { product ->
                findNavController().navigate(
                    HomeFragmentDirections.goToProductDetailFragment(
                        product
                    )
                )
            }
        }
    }
}
