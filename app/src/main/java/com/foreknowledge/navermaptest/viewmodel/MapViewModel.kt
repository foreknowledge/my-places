package com.foreknowledge.navermaptest.viewmodel

import android.util.Log
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foreknowledge.navermaptest.R
import com.foreknowledge.navermaptest.model.data.UserMarker
import com.foreknowledge.navermaptest.model.repository.NaverRepository
import com.foreknowledge.navermaptest.util.MarkerUtil
import com.foreknowledge.navermaptest.util.StringUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Utmk
import com.naver.maps.map.NaverMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Create by Yeji on 08,April,2020.
 */
class MapViewModel(
    private val repository: NaverRepository
): ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _focusedMarker = MutableLiveData<UserMarker?>()
    val focusedMarker: LiveData<UserMarker?> = _focusedMarker

    private val _btnText = MutableLiveData<String>()
    val btnText: LiveData<String> = _btnText

    private val _btnVisibility = MutableLiveData(false)
    val btnVisibility: LiveData<Boolean> = _btnVisibility

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> = _toastMsg

    private fun onMarkerClick(clickedMarker: UserMarker): Boolean {
        // 기존 focused marker 클릭 -> do nothing
        if (_focusedMarker.value == clickedMarker) return true

        MarkerUtil.detachUnsavedMarker(_focusedMarker.value)
        _focusedMarker.value = clickedMarker
        _btnText.value = StringUtil.getString(R.string.btn_delete)

        val pos = clickedMarker.marker.position
        requestMarkerAddr(pos.latitude, pos.longitude)

        return true
    }

    private fun requestMarkerAddr(lat: Double, lng: Double) {
        val utmk = Utmk.valueOf(LatLng(lat, lng))

        repository.getAddressInfo(utmk.x, utmk.y,
        failure = { tag, msg ->
            Log.d(tag, msg)
            _toastMsg.value = StringUtil.getString(R.string.request_failure)
        },
        success = { geoResponse ->
            Log.d("NaverMapTest", "response: $geoResponse")
        })
    }

    fun setMapClickListener(naverMap: NaverMap) =
        naverMap.setOnMapClickListener { _, coord ->
            MarkerUtil.detachUnsavedMarker(_focusedMarker.value)
            _focusedMarker.value =
                MarkerUtil.createUserMarker(coord.latitude, coord.longitude) { onMarkerClick(it) }
            _btnText.value = StringUtil.getString(R.string.btn_save)

            requestMarkerAddr(coord.latitude, coord.longitude)
        }

    fun Button.isSaveButton() = text == StringUtil.getString(R.string.btn_save)

    fun showButton() { _btnVisibility.value = true }

    fun hideButton() {
        _focusedMarker.value = null
        _btnVisibility.value = false
    }

    fun getAllMarkers() {
        coroutineScope.launch {
            val userMarkers = repository.getAllMarkers()

            launch(Dispatchers.Main) {
                userMarkers.forEach { userMarker ->
                    val pos = userMarker.marker.position
                    _focusedMarker.value =
                        MarkerUtil.createUserMarker(pos.latitude, pos.longitude, userMarker.id) { onMarkerClick(it) }
                }
            }
        }
    }

    fun addMarker(userMarker: UserMarker) {
        val pos = userMarker.marker.position
        coroutineScope.launch {
            userMarker.id = repository.addMarker(pos.latitude, pos.longitude)
            _toastMsg.postValue(StringUtil.getString(R.string.msg_saved))
        }
    }

    fun deleteMarker(userMarker: UserMarker) {
        val pos = userMarker.marker.position
        coroutineScope.launch {
            repository.deleteMarker(pos.latitude, pos.longitude, userMarker.id)
            _toastMsg.postValue(StringUtil.getString(R.string.msg_deleted))
        }
    }

}