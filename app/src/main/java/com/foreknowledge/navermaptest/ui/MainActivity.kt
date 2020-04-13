package com.foreknowledge.navermaptest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.UiThread
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.foreknowledge.navermaptest.R
import com.foreknowledge.navermaptest.databinding.ActivityMainBinding
import com.foreknowledge.navermaptest.model.repository.NaverRepository
import com.foreknowledge.navermaptest.viewmodel.MapViewModel
import com.foreknowledge.navermaptest.viewmodel.MainViewModelFactory
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
	private lateinit var binding: ActivityMainBinding
	private val viewModel by lazy {
		ViewModelProvider(this, MainViewModelFactory(NaverRepository(this@MainActivity)))[MapViewModel::class.java]
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

		binding.viewModel = viewModel
		binding.lifecycleOwner = this

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
		viewModel.initMarkers(naverMap)
		viewModel.setMapClickListener(naverMap)

		subscribeUi(naverMap)
	}

	private fun subscribeUi(naverMap: NaverMap) {
		with(viewModel) {
			focusedMarker.observe(this@MainActivity, Observer { it?.map = naverMap })
			btnText.observe(this@MainActivity, Observer { showButton() })
		}
	}
}
