package com.foreknowledge.navermaptest.util

import android.view.Gravity
import android.widget.Toast
import com.foreknowledge.navermaptest.GlobalApplication

/**
 * Created by Yeji on 13,April,2020.
 */
object ToastUtil {
    fun showToast(message: String) {
        val toast = Toast.makeText(GlobalApplication.getContext(), message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 100)
        toast.show()
    }
}