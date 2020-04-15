package com.foreknowledge.navermaptest.network

import com.foreknowledge.navermaptest.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Create by Yeji on 15,April,2020.
 */
object RetrofitBuilder {
	val service: GeoService = Retrofit.Builder()
		.baseUrl(BASE_URL)
		.addConverterFactory(GsonConverterFactory.create())
		.build()
		.create(GeoService::class.java)
}