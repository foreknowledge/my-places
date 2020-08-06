package com.foreknowledge.navermaptest.model.repository

import com.foreknowledge.navermaptest.network.GeoResponse
import com.foreknowledge.navermaptest.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Yeji on 15,April,2020.
 */
object GeoDataSource {
	private val TAG = GeoDataSource::class.java.simpleName
	private val serviceApi = RetrofitClient.service

	fun requestAddr(
		x: Double, y: Double,
		success:(response: GeoResponse?) -> Unit,
		failure:(String, String) -> Unit
	) {
		serviceApi.getAddress("$x,$y")
			.enqueue(object: Callback<GeoResponse> {
				override fun onResponse(call: Call<GeoResponse>, response: Response<GeoResponse>) {
					if (response.isSuccessful)
						success(response.body())
					else
						failure(TAG, "request failure: ${response.message()}")
				}

				override fun onFailure(call: Call<GeoResponse>, t: Throwable) {
					failure(TAG, "throwable: ${t.message}")
				}
			})
	}
}