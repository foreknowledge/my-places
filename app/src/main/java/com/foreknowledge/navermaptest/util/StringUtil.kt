package com.foreknowledge.navermaptest.util

import com.foreknowledge.navermaptest.GlobalApplication

/**
 * Created by Yeji on 13,April,2020.
 */
object StringUtil {
    fun getString(resId: Int) = GlobalApplication.getContext().getString(resId)
}