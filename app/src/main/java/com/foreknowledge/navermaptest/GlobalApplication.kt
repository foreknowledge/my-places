package com.foreknowledge.navermaptest

import android.app.Application
import android.content.Context

/**
 * Create by Yeji on 11,April,2020.
 */
class GlobalApplication: Application() {
    companion object {

        private var APPLICATION_CONTEXT: Context? = null

        @JvmStatic
        fun getContext(): Context {
            return APPLICATION_CONTEXT!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        APPLICATION_CONTEXT = applicationContext
    }
}