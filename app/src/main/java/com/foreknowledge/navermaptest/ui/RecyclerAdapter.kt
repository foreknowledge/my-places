package com.foreknowledge.navermaptest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foreknowledge.navermaptest.R
import com.foreknowledge.navermaptest.model.data.Place
import kotlinx.android.synthetic.main.list_item.view.*

/**
 * Create by Yeji on 19,April,2020.
 */
class RecyclerAdapter(
	private var places: List<Place>
): RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

	inner class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		private val address = view.address
		fun bind(address: String) {
			this.address.text = address
		}
	}

	fun updatePlaces(newPlaces: List<Place>) {
		places = newPlaces
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
		RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

	override fun getItemCount(): Int = places.size

	override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) =
		holder.bind(places[position].address ?: "")
}