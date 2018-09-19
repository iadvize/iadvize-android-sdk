package com.iadvize.conversation.sdk.demo.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.iadvize.conversation.sdk.demo.fragments.CartFragment
import com.iadvize.conversation.sdk.demo.fragments.CatalogFragment


/**
 * Created by Yann Coupé on 21/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class MainPagerAdapter(fm: FragmentManager, var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? =  when (position) {
            0 -> CatalogFragment()
            1 -> CartFragment()
            else -> null
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}