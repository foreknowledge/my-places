package com.foreknowledge.navermaptest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.UiThread
import androidx.databinding.DataBindingUtil
import com.foreknowledge.navermaptest.R
import com.foreknowledge.navermaptest.databinding.ActivityMainBinding
import com.foreknowledge.navermaptest.util.MapUtil
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

		initMapFragment()
	}

	private fun initMapFragment() {
		val mapFragment = (map as MapFragment?) ?: MapFragment.newInstance().also {
			supportFragmentManager.beginTransaction().add(R.id.map, it).commit()
		}

		mapFragment.getMapAsync(this)
	}

	@UiThread
	override fun onMapReady(naverMap: NaverMap) {
		setMapClickListener(naverMap)
	}

	private fun setMapClickListener(naverMap: NaverMap) {
		naverMap.setOnMapClickListener { _, coord ->
			MapUtil.createMarker(coord.latitude, coord.longitude).apply {
				map = naverMap
			}
		}
	}
}
