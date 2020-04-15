package com.foreknowledge.navermaptest.network

/**
 * Create by Yeji on 15,April,2020.
 */
data class GeoResponse (
	val results: List<ResultResponse>
)

data class ResultResponse (
	val name: String,
	val region: RegionResponse
)

data class RegionResponse (
	val area0: AreaResponse,
	val area1: AreaResponse,
	val area2: AreaResponse,
	val area3: AreaResponse,
	val area4: AreaResponse
)

data class AreaResponse (
	val name: String
)