package com.rongcheng.`as`.proj.common.ui.component

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

open class HiBaseActivity : AppCompatActivity() , HiBaseActionInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}