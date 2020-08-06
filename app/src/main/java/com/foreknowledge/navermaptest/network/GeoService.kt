package com.foreknowledge.navermaptest.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Yeji on 15,April,2020.
 */
interface GeoService {

	@GET("gc")
	fun getAddress(
		@Query("coord") coord: String,
		// 좌표 체계: UTM-K
		@Query("sourcecrs") sourcecrs: String = "nhn:2048",
		// 변환 작업: 좌표 -> 법정동 & 도로명 주소
		@Query("orders") orders: String = "legalcode,roadaddr",
		@Query("output") output: String = "json"
	): Call<GeoResponse>

}