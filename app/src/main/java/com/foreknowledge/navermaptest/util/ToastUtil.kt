package com.foreknowledge.navermaptest.util

import android.widget.Toast
import com.foreknowledge.navermaptest.GlobalApplication

/**
 * Create by Yeji on 13,April,2020.
 */
object ToastUtil {
    fun showToast(message: String) =
        Toast.makeText(GlobalApplication.getContext(), message, Toast.LENGTH_SHORT).show()
}