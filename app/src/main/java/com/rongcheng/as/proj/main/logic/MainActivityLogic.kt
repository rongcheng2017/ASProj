package com.rongcheng.`as`.proj.main.logic

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import com.rongcheng.`as`.proj.common.tab.HiFragmentTabView
import com.rongcheng.`as`.proj.common.tab.HiTabViewAdapter
import com.rongcheng.`as`.proj.main.R
import com.rongcheng.`as`.proj.main.fragment.*
import com.rongcheng.hi.ui.tab.bottom.HiTabBottomInfo
import com.rongcheng.hi.ui.tab.bottom.HiTabBottomLayout
import com.rongcheng.hi.ui.tab.common.OnTabSelectedListener
import java.util.*

class MainActivityLogic(
    private val activityProvider: ActivityProvider,
    savedInstanceState: Bundle?
) {
    companion object {
        const val SAVED_CURRENT_ID = "SAVED_CURRENT_ID"
    }

    var fragmentTabView: HiFragmentTabView? = null
        private set
    var hiTabBottomLayout: HiTabBottomLayout? = null
        private set
    var infoList: MutableList<HiTabBottomInfo<*>>? = null
        private set
    var currentItemIndex: Int = 0

    init {
        if (savedInstanceState != null) {
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID)
        }
        initTabBottom()
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SAVED_CURRENT_ID, currentItemIndex)
    }

    private fun initTabBottom() {
        hiTabBottomLayout = activityProvider.findViewById(R.id.tab_bottom_layout)
        hiTabBottomLayout?.bottomAlpha = 0.85f
        infoList = ArrayList()
        val defaultColor = activityProvider.getResources().getColor(R.color.tabBottomDefaultColor)
        val tintColor = activityProvider.getResources().getColor(R.color.tabBottomTintColor)
        val homeInfo =
            HiTabBottomInfo(
                name = "首页",
                iconFont = "fonts/iconfont.ttf",
                defaultIconName = activityProvider.getString(R.string.if_home),
                selectedIconName = null,
                defaultColor = defaultColor,
                tintColor = tintColor
            )
        homeInfo.fragment = HomePageFragment::class.java
        val infoRecommend =
            HiTabBottomInfo(
                name = "收藏",
                iconFont = "fonts/iconfont.ttf",
                defaultIconName = activityProvider.getString(R.string.if_favorite),
                selectedIconName = null,
                defaultColor = defaultColor,
                tintColor = tintColor
            )
        infoRecommend.fragment = FavoritePageFragment::class.java
        val infoCategory =
            HiTabBottomInfo(
                name = "分类",
                iconFont = "fonts/iconfont.ttf",
                defaultIconName = activityProvider.getString(R.string.if_category),
                selectedIconName = null,
                defaultColor = "#ff656667",
                tintColor = "#ffd44949"
            )
        infoCategory.fragment = CategoryPageFragment::class.java
        val infoChat =
            HiTabBottomInfo(
                name = "推荐",
                iconFont = "fonts/iconfont.ttf",
                defaultIconName = activityProvider.getString(R.string.if_recommend),
                selectedIconName = null,
                defaultColor = defaultColor,
                tintColor = tintColor
            )
        infoChat.fragment = RecommendPageFragment::class.java
        val infoProfile =
            HiTabBottomInfo(
                name = "我的",
                iconFont = "fonts/iconfont.ttf",
                defaultIconName = activityProvider.getString(R.string.if_profile),
                selectedIconName = null,
                defaultColor = defaultColor,
                tintColor = tintColor
            )
        infoProfile.fragment = ProfilePageFragment::class.java
        infoList?.apply {
            add(homeInfo)
            add(infoRecommend)
            add(infoCategory)
            add(infoChat)
            add(infoProfile)
            hiTabBottomLayout?.inflateInfo(this)
            initFragmentTabView()
            hiTabBottomLayout?.addTabSelectedChangeListener(object :
                OnTabSelectedListener<HiTabBottomInfo<*>> {
                override fun onTabSelectedChange(
                    index: Int,
                    prevInfo: HiTabBottomInfo<*>?,
                    nextInfo: HiTabBottomInfo<*>?
                ) {
                    fragmentTabView?.currentPosition = index
                    currentItemIndex = index
                }
            })
            hiTabBottomLayout?.defaultSelected(this[currentItemIndex])
        }


    }

    private fun initFragmentTabView() {
        infoList?.apply {
            val tabViewAdapter = HiTabViewAdapter(
                fragmentManager = activityProvider.getSupportFragmentManager(),
                infoList = this
            )
            fragmentTabView = activityProvider.findViewById(R.id.fragment_tab_view)
            fragmentTabView?.mAdapter = tabViewAdapter
        }

    }

    interface ActivityProvider {

        fun <T : View> findViewById(@IdRes id: Int): T
        fun getResources(): Resources

        fun getSupportFragmentManager(): FragmentManager

        fun getString(@StringRes resId: Int): String
    }
}