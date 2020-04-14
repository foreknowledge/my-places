package com.foreknowledge.navermaptest.util

import com.foreknowledge.navermaptest.GlobalApplication

/**
 * Create by Yeji on 13,April,2020.
 */
object StringUtil {
    fun getString(resId: Int) = GlobalApplication.getContext().getString(resId)
}