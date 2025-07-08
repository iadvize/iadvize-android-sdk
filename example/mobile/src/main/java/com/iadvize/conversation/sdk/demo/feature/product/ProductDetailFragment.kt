package com.iadvize.conversation.sdk.demo.feature.product

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.databinding.ProductDetailFragmentBinding
import com.iadvize.conversation.sdk.demo.feature.IAdvizeSDKConfig
import com.iadvize.conversation.sdk.demo.feature.service.ServiceAdapter
import com.iadvize.conversation.sdk.demo.utility.dpToPx
import com.iadvize.conversation.sdk.demo.utility.strikethrough
import com.iadvize.conversation.sdk.feature.visitor.CustomData
import kotlin.random.Random

class ProductDetailFragment : Fragment() {
    private val args: ProductDetailFragmentArgs by navArgs()
    private var binding: ProductDetailFragmentBinding? = null
    private var product: Product? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductDetailFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        product = args.product
        loadView()

        product?.productId?.let {
            // Set custom data
            val customData = CustomData.fromString("productId", it)
            IAdvizeSDK.visitorController.registerCustomData(listOf(customData))

            // Trigger targeting rule only on products with product id
            IAdvizeSDK.targetingController.activateTargetingRule(IAdvizeSDKConfig.targetingRule)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        IAdvizeSDK.targetingController.deactivateTargetingRule()
    }

    private fun loadView() {
        // Title
        binding?.name?.text = product?.name

        // Rating
        binding?.productRatingView?.reviewCount?.text =
            getString(R.string.reviews, Random.nextInt(2000))
        val stars = Random.nextInt(3) + 3
        val context = ContextThemeWrapper(requireContext(), R.style.Theme_Demo)
        (5 downTo 1).forEach { index ->
            val size = 16.dpToPx(context)
            val star = AppCompatImageView(context)
            star.layoutParams = LinearLayout.LayoutParams(size, size)
            star.setImageResource(R.drawable.ic_star)
            val color = if (index < stars) R.color.bright_sun else R.color.ghost
            star.imageTintList = ColorStateList.valueOf(resources.getColor(color))
            binding?.productRatingView?.root?.addView(star, 0)
        }

        // Pictures
        product?.pictureResId?.let { binding?.picture?.setImageResource(it) }

        // Prices
        binding?.newPrice?.text = product?.newPrice
        binding?.oldPrice?.text = product?.oldPrice
        binding?.oldPrice?.strikethrough()

        // Description
        binding?.description?.text = product?.description

        // Service List
        binding?.serviceListView?.recycler?.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            it.adapter = ServiceAdapter()
        }

        // Product List
        binding?.productListView?.title?.text = getString(R.string.other_products)
        binding?.productListView?.seeAll?.visibility = View.GONE
        binding?.productListView?.recycler?.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            it.adapter = ProductAdapter(products.filter { p -> p != product }.take(4)) { product ->
                findNavController().navigate(
                    ProductDetailFragmentDirections.goToProductDetailFragment(product)
                )
            }
        }
    }
}
