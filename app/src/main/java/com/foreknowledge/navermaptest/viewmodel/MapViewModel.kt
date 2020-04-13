package com.foreknowledge.navermaptest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foreknowledge.navermaptest.R
import com.foreknowledge.navermaptest.model.data.UserMarker
import com.foreknowledge.navermaptest.model.repository.NaverRepository
import com.foreknowledge.navermaptest.util.MarkerUtil
import com.foreknowledge.navermaptest.util.StringUtil
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
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

    private val _focusedMarker = MutableLiveData<Marker?>()
    val focusedMarker: LiveData<Marker?> = _focusedMarker

    private val _btnText = MutableLiveData<String>()
    val btnText: LiveData<String> = _btnText

    private val _btnVisibility = MutableLiveData(false)
    val btnVisibility: LiveData<Boolean> = _btnVisibility

    fun onButtonClick() =
        _focusedMarker.value?.let { marker ->
            if (btnText.value == StringUtil.getString(R.string.btn_save))
                addMarker(marker)
            else
                deleteMarker(marker)

            _focusedMarker.value = null
            hideButton()
        }

    fun onCancelClick() {
        if (btnText.value == StringUtil.getString(R.string.btn_save))
            _focusedMarker.value?.map = null

        _focusedMarker.value = null
        hideButton()
    }

    private fun onMarkerClick(clickedMarker: Marker): Boolean {
        // 기존 focused marker 클릭 -> do nothing
        if (_focusedMarker.value == clickedMarker) return true

        MarkerUtil.detachUnsavedMarker(_focusedMarker.value)
        _focusedMarker.value = clickedMarker
        _btnText.value = StringUtil.getString(R.string.btn_delete)

        return true
    }

    fun setMapClickListener(naverMap: NaverMap) =
        naverMap.setOnMapClickListener { _, coord ->
            MarkerUtil.detachUnsavedMarker(_focusedMarker.value)
            _focusedMarker.value =
                MarkerUtil.createAndAttachMarker(coord.latitude, coord.longitude, naverMap) { onMarkerClick(it) }
            _btnText.value = StringUtil.getString(R.string.btn_save)
        }

    fun initMarkers(naverMap: NaverMap) {
        coroutineScope.launch {
            val savedMarkers = repository.getAllMarkers()

            launch(Dispatchers.Main) {
                savedMarkers.forEach { coord ->
                    MarkerUtil.insertToList(
                        MarkerUtil.createAndAttachMarker(coord.lat, coord.lng, naverMap) { onMarkerClick(it) }
                    )
                }
            }
        }
    }

    private fun addMarker(marker: Marker) {
        val userMarker = UserMarker.fromMarker(marker)
        coroutineScope.launch {
            repository.addMarker(userMarker)

            launch(Dispatchers.Main) {
                MarkerUtil.insertToList(marker)
            }
        }
    }

    private fun deleteMarker(marker: Marker) {
        val userMarker = UserMarker.fromMarker(marker)
        coroutineScope.launch {
            repository.deleteMarker(userMarker)

            launch(Dispatchers.Main) {
                MarkerUtil.removeFromList(marker)
            }
        }
    }

    fun showButton() { _btnVisibility.value = true }
    private fun hideButton() { _btnVisibility.value = false }

}