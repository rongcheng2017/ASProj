package com.rongcheng.`as`.proj.common.tab

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

/**
 * 1.将Fragment的操作内聚
 * 2.提供通用的一些API
 */
class HiFragmentTabView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet?,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributes, defStyleAttr) {
    var currentPosition: Int = -1
        set(value) {
            if (value < 0 || value >= mAdapter?.getCount() ?: 0)
                return
            field = value
            mAdapter?.instantiateItem(this, position = value)
        }
    var mAdapter: HiTabViewAdapter? = null
        set(value) {
            if (mAdapter != null || value == null) return
            field = value
            currentPosition = -1
        }


    fun getCurrentFragment(): Fragment {
        return mAdapter?.mCurFragment ?: let {
            throw  IllegalArgumentException("please call setAdapter() first.")
        }
    }
}