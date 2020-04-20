package com.foreknowledge.navermaptest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foreknowledge.navermaptest.R
import com.foreknowledge.navermaptest.model.data.Place
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import kotlinx.android.synthetic.main.item_place.view.*

/**
 * Create by Yeji on 19,April,2020.
 */
class PlaceRecyclerAdapter(
	private var places: List<Place>,
	private val naverMap: NaverMap,
	private val placeItemClick:(place: Place) -> Unit
): RecyclerView.Adapter<PlaceRecyclerAdapter.PlaceViewHolder>() {

	inner class PlaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		private val itemPlace = view.item_place
		private val itemAddress = view.address
		fun bind(position: Int) {
			val place = places[position]

			itemAddress.text = place.address ?: ""
			itemPlace.setOnClickListener {
				placeItemClick(place)

				val cameraUpdate = CameraUpdate.scrollTo(place.marker.position)
					.animate(CameraAnimation.Easing)
				naverMap.moveCamera(cameraUpdate)
			}
		}
	}

	fun updatePlaces(newPlaces: List<Place>) {
		places = newPlaces
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder =
		PlaceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false))

	override fun getItemCount(): Int = places.size

	override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) = holder.bind(position)
}