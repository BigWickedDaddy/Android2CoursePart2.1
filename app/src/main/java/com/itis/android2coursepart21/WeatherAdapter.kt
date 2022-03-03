package com.itis.android2coursepart21

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.android2coursepart21.data.api.response.NearWeather

class WeatherAdapter(
    private val list: List<NearWeather>,
    private val action: (Int) -> (Unit)
): RecyclerView.Adapter<WeatherHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherHolder = WeatherHolder.create(parent, action)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.bind(list[position])
    }

}