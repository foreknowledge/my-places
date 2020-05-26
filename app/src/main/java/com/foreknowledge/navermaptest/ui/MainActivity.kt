package com.foreknowledge.navermaptest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.UiThread
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.foreknowledge.navermaptest.LOCATION_PERMISSION_REQUEST_CODE
import com.foreknowledge.navermaptest.R
import com.foreknowledge.navermaptest.databinding.ActivityMainBinding
import com.foreknowledge.navermaptest.model.repository.NaverRepository
import com.foreknowledge.navermaptest.util.ToastUtil
import com.foreknowledge.navermaptest.viewmodel.MapViewModel
import com.foreknowledge.navermaptest.viewmodel.MainViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("UNUSED_PARAMETER")
class MainActivity : AppCompatActivity(), OnMapReadyCallback {
	private lateinit var binding: ActivityMainBinding
	private val viewModel by lazy {
		ViewModelProvider(
			this,
			MainViewModelFactory(NaverRepository(this@MainActivity))
		)[MapViewModel::class.java]
	}

	private lateinit var placeRecyclerAdapter: PlaceRecyclerAdapter
	private lateinit var locationSource: FusedLocationSource

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

		initView()

		// 현재 위치 권한 요청
		locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
	}

	private fun initView() {
		binding.viewModel = viewModel
		binding.lifecycleOwner = this

		// map fragment
		(map as MapFragment).getMapAsync(this)
	}

	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<String>,
		grantResults: IntArray
	) {
		if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults))
			return

		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
	}

	@UiThread
	override fun onMapReady(naverMap: NaverMap) {
		viewModel.getAllPlaces()
		viewModel.setMapClickListener(naverMap)

		initBottomSheet(naverMap)
		initLocation(naverMap)
		subscribeUi(naverMap)
	}

	private fun initBottomSheet(naverMap: NaverMap) {
		BottomSheetBehavior.from(binding.bottomSheet.container)

		placeRecyclerAdapter =
			PlaceRecyclerAdapter(naverMap) { place ->
			viewModel.placesItemClick(place)
		}

		with (binding.bottomSheet.placeList) {
			layoutManager = LinearLayoutManager(this@MainActivity)
			adapter = placeRecyclerAdapter
		}
	}

	private fun initLocation(naverMap: NaverMap) {
		// 위치 오버레이
		val locationOverlay = naverMap.locationOverlay
		locationOverlay.isVisible = true

		// 위치 추적 모드
		naverMap.locationSource = locationSource
		naverMap.locationTrackingMode = LocationTrackingMode.NoFollow

		// 현재 위치 버튼 활성화
		naverMap.uiSettings.isLocationButtonEnabled = true
	}

	private fun subscribeUi(naverMap: NaverMap) {
		with(viewModel) {
			focusedPlace.observe(this@MainActivity, Observer { it?.marker?.map = naverMap })
			addressText.observe(this@MainActivity, Observer { if (it.isNotBlank()) showAddress() })
			toastMsg.observe(this@MainActivity, Observer { ToastUtil.showToast(it) })
			placeList.observe(this@MainActivity, Observer {
				placeRecyclerAdapter.updatePlaces(it)
				binding.placeCount = placeRecyclerAdapter.itemCount
			})
		}
	}

	fun fabClick(view: View) {
		with(viewModel) {
			focusedPlace.value?.let { place ->
				if (!isSavedPlace.value!!)
					addPlace(place)
				else {
					deletePlace(place)
					place.marker.map = null
				}
			}
		}
	}

	fun cancelClick(view: View) {
		with(viewModel) {
			// 임시로 만든 place 삭제
			if (!isSavedPlace.value!!)
				focusedPlace.value?.marker?.map = null

			hideAddress()
		}
	}

}