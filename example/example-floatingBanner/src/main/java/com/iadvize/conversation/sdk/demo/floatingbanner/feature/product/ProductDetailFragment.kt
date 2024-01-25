package com.iadvize.conversation.sdk.demo.floatingbanner.feature.product

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.feature.HideButton
import com.iadvize.conversation.sdk.demo.feature.ShowButton
import com.iadvize.conversation.sdk.demo.feature.product.ProductAdapter
import com.iadvize.conversation.sdk.demo.feature.product.products
import com.iadvize.conversation.sdk.demo.feature.service.ServiceAdapter
import com.iadvize.conversation.sdk.demo.floatingbanner.R
import com.iadvize.conversation.sdk.demo.floatingbanner.databinding.ProductDetailFragmentBinding
import com.iadvize.conversation.sdk.demo.floatingbanner.feature.IAdvizeSDKConfig
import com.iadvize.conversation.sdk.demo.floatingbanner.feature.uiScope
import com.iadvize.conversation.sdk.demo.utility.dpToPx
import com.iadvize.conversation.sdk.demo.utility.on
import com.iadvize.conversation.sdk.demo.utility.strikethrough
import com.iadvize.conversation.sdk.feature.targeting.NavigationOption
import kotlin.random.Random


class ProductDetailFragment : Fragment() {
    private val args: ProductDetailFragmentArgs by navArgs()
    private var binding: ProductDetailFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductDetailFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadView()

        // Hook to button update events
        on<ShowButton> { showBanner() }
        on<HideButton> { hideBanner() }

        // Open chatbox on banner click
        binding?.banner?.setOnClickListener {
            IAdvizeSDK.chatboxController.presentChatbox(requireContext())
        }

        // Targeting rule is triggered on Product page opening
        binding?.root?.postDelayed(1500) {
            IAdvizeSDK.targetingController.registerUserNavigation(
                NavigationOption.ActivateNewRule(IAdvizeSDKConfig.targetingRule)
            )
        }
    }

    private fun loadView() {
        val product = args.product

        // Title
        binding?.name?.text = product.name

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
        binding?.picture?.setImageResource(product.pictureResId)

        // Prices
        binding?.newPrice?.text = product.newPrice
        binding?.oldPrice?.text = product.oldPrice
        binding?.oldPrice?.strikethrough()

        // Description
        binding?.description?.text = product.description

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

    private fun showBanner() = uiScope {
        binding?.banner?.visibility = VISIBLE
        toggleBanner()
    }

    private fun hideBanner() = uiScope {
        binding?.banner?.visibility = GONE
        shrinkBanner()
    }

    private fun toggleBanner() = uiScope {
        binding?.banner?.let { banner ->
            banner.postDelayed(1500) {
                if (banner.isVisible) {
                    extendBanner()
                    banner.postDelayed(2500) {
                        if (banner.isVisible) shrinkBanner()
                    }
                }
            }
        }
    }

    private fun extendBanner() = uiScope {
        binding?.container?.let { container ->
            val constraintSet = ConstraintSet()
            constraintSet.clone(container)
            constraintSet.constrainWidth(R.id.banner, MATCH_PARENT)

            TransitionManager.beginDelayedTransition(container, ChangeBounds().apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = 500
            })

            binding?.bannerTitle?.visibility = VISIBLE
            binding?.bannerSubtitle?.visibility = VISIBLE
            binding?.bannerAction?.visibility = VISIBLE

            constraintSet.applyTo(container)
        }
    }

    private fun shrinkBanner() = uiScope {
        binding?.container?.let { container ->
            val constraintSet = ConstraintSet()
            constraintSet.clone(container)
            constraintSet.constrainWidth(R.id.banner, WRAP_CONTENT)

            TransitionManager.beginDelayedTransition(container, ChangeBounds().apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = 500
            })

            binding?.bannerTitle?.visibility = GONE
            binding?.bannerSubtitle?.visibility = GONE
            binding?.bannerAction?.visibility = GONE

            constraintSet.applyTo(container)
        }
    }
}
