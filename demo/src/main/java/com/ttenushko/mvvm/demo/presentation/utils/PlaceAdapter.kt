package com.ttenushko.mvvm.demo.presentation.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ttenushko.mvvm.demo.databinding.ItemPlaceBinding
import com.ttenushko.mvvm.demo.domain.weather.model.Place

class PlaceAdapter(
    private val callback: Callback
) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    private var items: List<Place> = listOf()

    fun set(items: List<Place>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun clear() {
        if (this.items.isNotEmpty()) {
            this.items = listOf()
            notifyDataSetChanged()
        }
    }

    fun getItemAt(position: Int): Place =
        items[position]

    override fun getItemCount(): Int =
        items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemPlaceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = items[position]
        holder.bind(place)
    }

    private fun handleItemClicked(position: Int) {
        callback.onItemClicked(items[position])
    }

    inner class ViewHolder(
        private val viewBinding: ItemPlaceBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        init {
            viewBinding.root.setOnClickListener {
                adapterPosition.let { position ->
                    if (RecyclerView.NO_POSITION != position) {
                        handleItemClicked(position)
                    }
                }
            }
        }

        fun bind(place: Place) {
            viewBinding.placeTitle.text =
                "${place.name}, ${place.countyCode.toUpperCase()}"
            viewBinding.placeLocation.text =
                "%.6f, %.6f".format(place.location.latitude, place.location.longitude)
        }
    }

    interface Callback {
        fun onItemClicked(place: Place)
    }
}