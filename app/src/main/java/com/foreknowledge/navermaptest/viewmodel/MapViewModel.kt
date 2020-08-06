package com.foreknowledge.navermaptest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foreknowledge.navermaptest.R
import com.foreknowledge.navermaptest.model.data.Place
import com.foreknowledge.navermaptest.model.repository.NaverRepository
import com.foreknowledge.navermaptest.util.GeoUtil.convertStr
import com.foreknowledge.navermaptest.util.PlaceUtil
import com.foreknowledge.navermaptest.util.StringUtil
import com.naver.maps.map.NaverMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Yeji on 08,April,2020.
 */
class MapViewModel(
    private val repository: NaverRepository
): ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val places = mutableMapOf<Long, Place>()

    private val _placeList = MutableLiveData<List<Place>>()
    val placeList: LiveData<List<Place>> = _placeList

    private val _focusedPlace = MutableLiveData<Place?>()
    val focusedPlace: LiveData<Place?> = _focusedPlace

    private val _addressText = MutableLiveData("")
    val addressText: LiveData<String> = _addressText

    private val _isSavedPlace = MutableLiveData<Boolean>()
    val isSavedPlace: LiveData<Boolean> = _isSavedPlace

    private val _addressVisibility = MutableLiveData(false)
    val addressVisibility: LiveData<Boolean> = _addressVisibility

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> = _toastMsg

    private fun onPlaceClick(clickedPlace: Place): Boolean {
        // 기존 focused place 클릭 -> do nothing
        if (_focusedPlace.value == clickedPlace) return true

        PlaceUtil.detachUnsavedPlace(_focusedPlace.value)
        setFocusedPlace(clickedPlace)
        _isSavedPlace.value = true

        val pos = clickedPlace.marker.position
        requestPlaceAddr(pos.latitude, pos.longitude)

        return true
    }

    private fun requestPlaceAddr(lat: Double, lng: Double) =
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
            PlaceUtil.detachUnsavedPlace(_focusedPlace.value)
            setFocusedPlace(PlaceUtil.createPlace(coord.latitude, coord.longitude) { onPlaceClick(it) })
            _isSavedPlace.value = false

            requestPlaceAddr(coord.latitude, coord.longitude)
        }

    fun showAddress() { _addressVisibility.value = true }

    fun hideAddress() {
        PlaceUtil.loseFocus(_focusedPlace.value?.marker)
        _focusedPlace.postValue(null)
        _addressText.postValue("")
        _addressVisibility.postValue(false)
    }

    private fun setFocusedPlace(place: Place) {
        PlaceUtil.loseFocus(_focusedPlace.value?.marker)
        PlaceUtil.getFocus(place.marker)
        _focusedPlace.value = place
    }

    fun placesItemClick(place: Place) {
        PlaceUtil.detachUnsavedPlace(_focusedPlace.value)
        setFocusedPlace(place)
        _isSavedPlace.value = true
        _addressText.value = place.address
    }

    fun getAllPlaces() {
        coroutineScope.launch {
            repository.getAllPlaces().forEach { place ->
                val pos = place.marker.position
                launch (Dispatchers.Main) {
                    _focusedPlace.value = place.apply {
                        marker.setOnClickListener { onPlaceClick(this) }
                    }

                    repository.getAddressInfo(
                        pos.latitude, pos.longitude,
                        success = { response ->
                            places[place.id] = place.apply {
                                address = response?.convertStr()
                            }
                            _placeList.value = places.values.toList()
                        },
                        failure = { tag, msg ->
                            Log.e(tag, msg)
                            _toastMsg.value = StringUtil.getString(R.string.request_failure)
                        }
                    )
                }
            }

            _focusedPlace.postValue(null)
        }
    }

    fun addPlace(place: Place) {
        val pos = place.marker.position
        coroutineScope.launch {
            place.id = repository.addPlace(pos.latitude, pos.longitude)
            places[place.id] = Place(place.id, place.marker, addressText.value)
            _placeList.postValue(places.values.toList())
            _toastMsg.postValue(StringUtil.getString(R.string.msg_saved))

            hideAddress()
        }
    }

    fun deletePlace(place: Place) {
        val pos = place.marker.position
        coroutineScope.launch {
            repository.deletePlace(pos.latitude, pos.longitude, place.id)
            places.remove(place.id)
            _placeList.postValue(places.values.toList())
            _toastMsg.postValue(StringUtil.getString(R.string.msg_deleted))

            hideAddress()
        }
    }

}