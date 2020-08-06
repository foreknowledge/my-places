package com.foreknowledge.navermaptest.util

import android.util.Log
import com.foreknowledge.navermaptest.network.GeoResponse
import java.lang.Exception

/**
 * Created by Yeji on 16,April,2020.
 */
object GeoUtil {
	private const val TAG = "GeoUtil"

	fun GeoResponse.convertStr(): String? {
		var address = ""

		try {
			val region = results[0].region
			address += region.area1.name + " " +
					region.area2.name + " " +
					region.area3.name + " " +
					region.area4.name

			if (results.size == 2) {
				val land = results[1].land
				address += land.addition0.name
			}
		} catch (e: Exception) {
			Log.e(TAG, "${e.message}")
		}

		return if (address.isEmpty()) null else address
	}
}