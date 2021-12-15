package com.rongcheng.`as`.proj.common.tab

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.rongcheng.hi.ui.tab.bottom.HiTabBottomInfo

class HiTabViewAdapter(
    val infoList: List<HiTabBottomInfo<*>>,
    val fragmentManager: FragmentManager
) {
    var mCurFragment: Fragment? = null
        private set


    /**
     * 实例化以及显示指定位置的Fragment
     */
    fun instantiateItem(container: View, position: Int) {
        val mCurTransaction = fragmentManager.beginTransaction()
        mCurFragment?.let {
            mCurTransaction.hide(it)
        }
        val name = "${container.id}:$position"
        var fragment = fragmentManager.findFragmentByTag(name)
        fragment?.apply {
            mCurTransaction.show(this)
        } ?: apply {
            fragment = getItem(position)
            fragment?.let {
                if (!it.isAdded) {
                    mCurTransaction.add(container.id, it, name)
                }
            }
        }
        mCurFragment = fragment
        mCurTransaction.commitAllowingStateLoss()
    }

    private fun getItem(position: Int): Fragment? {
        try {
            return infoList[position].fragment?.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null

    }
    fun getCount():Int{
        return infoList.size
    }
}