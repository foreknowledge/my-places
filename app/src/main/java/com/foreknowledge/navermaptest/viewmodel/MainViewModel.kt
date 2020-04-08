package com.foreknowledge.navermaptest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foreknowledge.navermaptest.model.data.MarkerPos
import com.foreknowledge.navermaptest.model.repository.NaverRepository
import com.foreknowledge.navermaptest.util.MapUtil
import com.naver.maps.map.overlay.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Create by Yeji on 08,April,2020.
 */
class MainViewModel(
    private val repository: NaverRepository
): ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val _markerList = MutableLiveData<List<Marker>>()

    val markerList: LiveData<List<Marker>> = _markerList

    fun addMarker(marker: Marker) {
        coroutineScope.launch {
            repository.addMarker(MarkerPos.fromMarker(marker))
        }
    }

    fun initAllMarkers() {
        coroutineScope.launch {
            val temp = mutableListOf<Marker>()
            repository.getAllMarkers().forEach {
                temp.add(MapUtil.createMarker(it.lat, it.lng))
            }
            _markerList.postValue(temp)
        }
    }

    fun deleteMarker(markerPos: MarkerPos) {
        coroutineScope.launch {
            repository.deleteMarker(markerPos)
        }
    }

}