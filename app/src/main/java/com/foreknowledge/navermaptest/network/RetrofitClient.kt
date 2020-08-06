package com.foreknowledge.navermaptest.network

import com.foreknowledge.navermaptest.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Yeji on 15,April,2020.
 */
object RetrofitClient {
	val service: GeoService = initService()

	private fun initService(): GeoService {
		val okHttpClient = OkHttpClient.Builder()
			.addInterceptor(HeaderInterceptor())
			.build()

		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(okHttpClient)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(GeoService::class.java)
	}
}
