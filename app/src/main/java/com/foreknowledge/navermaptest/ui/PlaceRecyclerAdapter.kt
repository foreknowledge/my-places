package com.foreknowledge.navermaptest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foreknowledge.navermaptest.databinding.ItemPlaceBinding
import com.foreknowledge.navermaptest.model.data.Place
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap

/**
 * Created by Yeji on 19,April,2020.
 */
class PlaceRecyclerAdapter(
	private val naverMap: NaverMap,
	private val placeItemClick:(place: Place) -> Unit
): ListAdapter<Place, PlaceRecyclerAdapter.PlaceViewHolder>(PlaceDiffCallback()) {

	inner class PlaceViewHolder(private val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(position: Int) {
			val place = currentList[position]

			binding.address = place.address ?: ""
			binding.tvAddress.setOnClickListener {
				placeItemClick(place)

				val cameraUpdate = CameraUpdate.scrollTo(place.marker.position)
					.animate(CameraAnimation.Easing)
				naverMap.moveCamera(cameraUpdate)
			}
		}
	}

	fun updatePlaces(newPlaces: List<Place>) {
		submitList(newPlaces)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlaceViewHolder(
		ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
	)

	override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) = holder.bind(position)
}