package com.foreknowledge.navermaptest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foreknowledge.navermaptest.R
import com.foreknowledge.navermaptest.model.data.Place
import com.foreknowledge.navermaptest.model.data.UserMarker
import com.foreknowledge.navermaptest.model.repository.NaverRepository
import com.foreknowledge.navermaptest.util.GeoUtil.convertStr
import com.foreknowledge.navermaptest.util.MarkerUtil
import com.foreknowledge.navermaptest.util.StringUtil
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
    private val places = mutableMapOf<Long, Place>()

    private val _placeList = MutableLiveData<List<Place>>()
    val placeList: LiveData<List<Place>> = _placeList

    private val _focusedMarker = MutableLiveData<UserMarker?>()
    val focusedMarker: LiveData<UserMarker?> = _focusedMarker

    private val _addressText = MutableLiveData("")
    val addressText: LiveData<String> = _addressText

    private val _isSavedMarker = MutableLiveData<Boolean>()
    val isSavedMarker: LiveData<Boolean> = _isSavedMarker

    private val _addressVisibility = MutableLiveData(false)
    val addressVisibility: LiveData<Boolean> = _addressVisibility

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> = _toastMsg

    private fun onMarkerClick(clickedMarker: UserMarker): Boolean {
        // 기존 focused marker 클릭 -> do nothing
        if (_focusedMarker.value == clickedMarker) return true

        MarkerUtil.detachUnsavedMarker(_focusedMarker.value)
        _focusedMarker.value = clickedMarker
        _isSavedMarker.value = true

        val pos = clickedMarker.marker.position
        requestMarkerAddr(pos.latitude, pos.longitude)

        return true
    }

    private fun requestMarkerAddr(lat: Double, lng: Double) =
        repository.getAddressInfo(
            lat, lng,
            failure = { tag, msg ->
                Log.e(tag, msg)
                _toastMsg.value = StringUtil.getString(R.string.request_failure)
            },
            success = { geoResponse ->
                _addressText.value = geoResponse?.convertStr()
                    ?: StringUtil.getString(R.string.no_result)
            })

    fun setMapClickListener(naverMap: NaverMap) =
        naverMap.setOnMapClickListener { _, coord ->
            MarkerUtil.detachUnsavedMarker(_focusedMarker.value)
            _focusedMarker.value =
                MarkerUtil.createUserMarker(coord.latitude, coord.longitude) { onMarkerClick(it) }
            _isSavedMarker.value = false

            requestMarkerAddr(coord.latitude, coord.longitude)
        }

    fun showAddress() { _addressVisibility.value = true }

    fun hideAddress() {
        _focusedMarker.postValue(null)
        _addressText.postValue("")
        _addressVisibility.postValue(false)
    }

    fun getAllMarkers() {
        coroutineScope.launch {
            repository.getAllMarkers().forEach { userMarker ->
                val pos = userMarker.marker.position
                launch (Dispatchers.Main) {
                    _focusedMarker.value =
                        MarkerUtil.createUserMarker(pos.latitude, pos.longitude, userMarker.id) { onMarkerClick(it) }
                }

                repository.getAddressInfo(
                    pos.latitude, pos.longitude,
                    success = { response ->
                        places[userMarker.id] = Place(pos.latitude, pos.longitude, response?.convertStr())
                        _placeList.postValue(places.values.toList())
                    },
                    failure = { tag, msg ->
                        Log.e(tag, msg)
                        _toastMsg.postValue(StringUtil.getString(R.string.request_failure))
                    }
                )
            }

            launch(Dispatchers.Main) { _focusedMarker.value = null }
        }
    }

    fun addMarker(userMarker: UserMarker) {
        val pos = userMarker.marker.position
        coroutineScope.launch {
            userMarker.id = repository.addMarker(pos.latitude, pos.longitude)
            places[userMarker.id] = Place(pos.latitude, pos.longitude, addressText.value)
            _placeList.postValue(places.values.toList())
            _toastMsg.postValue(StringUtil.getString(R.string.msg_saved))

            hideAddress()
        }
    }

    fun deleteMarker(userMarker: UserMarker) {
        val pos = userMarker.marker.position
        coroutineScope.launch {
            repository.deleteMarker(pos.latitude, pos.longitude, userMarker.id)
            places.remove(userMarker.id)
            _placeList.postValue(places.values.toList())
            _toastMsg.postValue(StringUtil.getString(R.string.msg_deleted))

            hideAddress()
        }
    }

}