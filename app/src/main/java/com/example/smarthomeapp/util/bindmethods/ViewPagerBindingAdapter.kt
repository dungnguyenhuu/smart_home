package com.example.smarthomeapp.util.bindmethods

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.tabs.TabLayout

object ViewPagerBindingAdapter {
    @BindingAdapter("vpAdapter")
    @JvmStatic
    fun setViewPagerAdapter(viewPager: ViewPager, adapter: PagerAdapter?) {
        viewPager.adapter = adapter
    }

    @BindingAdapter("vpOffScreenPageLimit")
    @JvmStatic
    fun setOffScreenPageLimit(viewPager: ViewPager, limit: Int) {
        viewPager.offscreenPageLimit = limit
    }

    @BindingAdapter("setupWithViewPager")
    @JvmStatic
    fun setupWithViewPager(tabLayout: TabLayout, viewPager: ViewPager) {
        tabLayout.setupWithViewPager(viewPager)
    }

    @BindingAdapter("currentTabAttrChanged")
    @JvmStatic
    fun setListeners(view: ViewPager, attrChange: InverseBindingListener) {
        view.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                attrChange.onChange()
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    @BindingAdapter("currentTab")
    @JvmStatic
    fun setCurrentTab(
        pager: ViewPager,
        liveCurrentTab: MutableLiveData<Int>
    ) {
        liveCurrentTab.value?.let {
            if (it != pager.currentItem) {
                pager.setCurrentItem(it, true)
            }
        }
    }

    @InverseBindingAdapter(attribute = "currentTab", event = "currentTabAttrChanged")
    @JvmStatic
    fun getCurrentTab(viewPager: ViewPager) = viewPager.currentItem
}