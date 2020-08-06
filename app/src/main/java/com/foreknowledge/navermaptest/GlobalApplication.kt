package com.foreknowledge.navermaptest

import android.app.Application
import android.content.Context
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.NaverMapSdk.NaverCloudPlatformClient

/**
 * Created by Yeji on 11,April,2020.
 */
class GlobalApplication: Application() {
    companion object {
        private lateinit var APPLICATION_CONTEXT: Context

        @JvmStatic
        fun getContext(): Context {
            return APPLICATION_CONTEXT
        }
    }

    override fun onCreate() {
        super.onCreate()
        APPLICATION_CONTEXT = applicationContext

        NaverMapSdk.getInstance(this).client =
            NaverCloudPlatformClient(BuildConfig.NAVER_CLIENT_ID)
    }
}