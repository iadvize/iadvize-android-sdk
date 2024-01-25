package com.iadvize.conversation.sdk.demo.discreet.feature.product

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.discreet.R
import com.iadvize.conversation.sdk.demo.discreet.databinding.ProductDetailFragmentBinding
import com.iadvize.conversation.sdk.demo.discreet.feature.IAdvizeSDKConfig
import com.iadvize.conversation.sdk.demo.discreet.feature.uiScope
import com.iadvize.conversation.sdk.demo.feature.HideButton
import com.iadvize.conversation.sdk.demo.feature.ShowButton
import com.iadvize.conversation.sdk.demo.feature.product.ProductAdapter
import com.iadvize.conversation.sdk.demo.feature.product.products
import com.iadvize.conversation.sdk.demo.feature.service.ServiceAdapter
import com.iadvize.conversation.sdk.demo.utility.dpToPx
import com.iadvize.conversation.sdk.demo.utility.on
import com.iadvize.conversation.sdk.demo.utility.strikethrough
import com.iadvize.conversation.sdk.feature.targeting.NavigationOption
import kotlin.math.hypot
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
        on<ShowButton> { showButton() }
        on<HideButton> { hideButton() }

        // Open chatbox on button click
        binding?.discreetButton?.setOnClickListener {
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

    private fun showButton() = uiScope {
        binding?.discreetButton?.let { fab ->
            fab.visibility = INVISIBLE
            fab.isExtended = false

            val cx = fab.width / 2
            val cy = fab.height / 2
            val radius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

            fab.visibility = VISIBLE
            ViewAnimationUtils
                .createCircularReveal(fab, cx, cy, 0f, radius)
                .apply { doOnEnd { toggleFab() } }
                .start()
        }
    }

    private fun hideButton() = uiScope {
        binding?.discreetButton?.visibility = GONE
    }

    private fun toggleFab() = uiScope {
        binding?.discreetButton?.let { fab ->
            fab.postDelayed(1500) {
                if (fab.isVisible) {
                    fab.extend()
                    fab.postDelayed(1500) {
                        if (fab.isVisible) fab.shrink()
                    }
                }
            }
        }
    }
}
