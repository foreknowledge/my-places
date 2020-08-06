package com.foreknowledge.navermaptest.network

import com.google.gson.annotations.SerializedName

/**
 * Created by Yeji on 15,April,2020.
 */
data class GeoResponse (
	@SerializedName("results")
	val results: List<ResultResponse>
)

data class ResultResponse (
	@SerializedName("name")
	val convertName: String,
	@SerializedName("region")
	val region: RegionResponse,
	@SerializedName("land")
	val land: LandResponse
)

data class RegionResponse (
	@SerializedName("area1")
	val area1: AreaResponse,
	@SerializedName("area2")
	val area2: AreaResponse,
	@SerializedName("area3")
	val area3: AreaResponse,
	@SerializedName("area4")
	val area4: AreaResponse
)

data class AreaResponse (
	@SerializedName("name")
	val name: String
)

data class LandResponse (
	@SerializedName("addition0")
	val addition0: AdditionResponse
)

data class AdditionResponse (
	@SerializedName("value")
	val name: String
)