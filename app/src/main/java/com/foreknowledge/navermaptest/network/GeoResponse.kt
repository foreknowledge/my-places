package com.foreknowledge.navermaptest.network

/**
 * Create by Yeji on 15,April,2020.
 */
data class GeoResponse (
	val results: List<ResultResponse>
)

data class ResultResponse (
	val name: String,
	val region: RegionResponse,
	val land: LandResponse
)

data class RegionResponse (
	val area0: AreaResponse,
	val area1: AreaResponse,
	val area2: AreaResponse,
	val area3: AreaResponse
)

data class AreaResponse (
	val name: String
)

data class LandResponse (
	val addition0: AdditionResponse,
	val addition1: AdditionResponse,
	val addition2: AdditionResponse
)

data class AdditionResponse (
	val type: String,
	val value: String
)