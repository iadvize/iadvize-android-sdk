package com.iadvize.conversation.sdk.demo.integratedbanner.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.common.databinding.HomeFragmentBinding
import com.iadvize.conversation.sdk.demo.feature.product.ProductAdapter
import com.iadvize.conversation.sdk.demo.feature.product.products
import com.iadvize.conversation.sdk.demo.feature.service.ServiceAdapter
import com.iadvize.conversation.sdk.demo.utility.strikethrough
import com.iadvize.conversation.sdk.demo.utility.underline
import com.iadvize.conversation.sdk.feature.targeting.NavigationOption

class HomeFragment : Fragment() {
    private var binding: HomeFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadView()

        // No rule triggered on Home page
        IAdvizeSDK.targetingController.registerUserNavigation(NavigationOption.ClearActiveRule)
    }

    private fun loadView() {
        // Promo
        binding?.promotionView?.oldPrice?.strikethrough()

        // Product List
        binding?.productListView?.seeAll?.let {
            it.underline()
            it.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.goToProductListFragment())
            }
        }
        binding?.productListView?.recycler?.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            it.adapter = ProductAdapter(products.shuffled().take(4)) { product ->
                findNavController().navigate(
                    HomeFragmentDirections.goToProductDetailFragment(
                        product
                    )
                )
            }
        }

        // Service List
        binding?.serviceListView?.recycler?.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            it.adapter = ServiceAdapter()
        }
    }
}
