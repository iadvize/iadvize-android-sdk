package com.iadvize.conversation.sdk.demo.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.iadvize.conversation.sdk.demo.fragments.CartFragment
import com.iadvize.conversation.sdk.demo.fragments.CatalogFragment

/**
 * Created by Yann Coupé on 21/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class MainPagerAdapter(fm: FragmentManager, var mNumOfTabs: Int) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = if (position == 0) {
        CatalogFragment()
    } else {
        CartFragment()
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}