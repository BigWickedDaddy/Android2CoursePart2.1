package com.itis.android2coursepart21.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.itis.android2coursepart21.data.WeatherRepositoryImpl
import com.itis.android2coursepart21.data.api.response.NearWeather
import com.itis.android2coursepart21.databinding.WeatherBinding
import kotlin.math.roundToInt
import coil.load
import com.itis.android2coursepart21.R


class WeatherHolder(
    private val binding: WeatherBinding,
    private val action: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NearWeather) {
        with(binding) {
            tvCityName.text = item.name
            tvCityTemperatureMain.text = WeatherRepositoryImpl.WeatherDataHandler.convertTemperature(item.main.temp)
//            val uri: Uri =
//                Uri.parse("https://openweathermap.org/img/wn/${detail.weather[0].icon}@2x.png")
            ivImage.load(
                WeatherRepositoryImpl.WeatherDataHandler.setImageIconUrl(item.weather[0].icon)
            )
        }
        itemView.setOnClickListener {
            action(item.id)
        }
        setMainBackgroundColor(itemView, item)
    }

    private fun setColor(itemView: View, colorId: Int){
        itemView.setBackgroundColor(
            ContextCompat.getColor(
                itemView.context,
                colorId
            )
        )
    }

    private fun setMainBackgroundColor(itemView: View, item: NearWeather) {
        when (item.main.temp.roundToInt()) {

            in WEATHER_TOOCOLD..WEATHER_COLD -> setColor(itemView, R.color.too_cold)
            in WEATHER_COLD..WEATHER_MINUS -> setColor(itemView, R.color.frozen)
            in WEATHER_MINUS..WEATHER_ZERO -> setColor(itemView, R.color.cold)
            in WEATHER_ZERO..WEATHER_PLUS -> setColor(itemView, R.color.neutral)
            in WEATHER_PLUS..WEATHER_WARM -> setColor(itemView, R.color.warm)
            in WEATHER_WARM..WEATHER_TOOWARM -> setColor(itemView, R.color.hot)

        }
    }

    companion object {
        private const val WEATHER_TOOWARM = 50
        private const val WEATHER_WARM = 25
        private const val WEATHER_PLUS = 5
        private const val WEATHER_ZERO = 0
        private const val WEATHER_MINUS = -5
        private const val WEATHER_COLD = -25
        private const val WEATHER_TOOCOLD = -50


        fun create(
            parent: ViewGroup,
            action: (Int) -> Unit
        ) = WeatherHolder(
            WeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            action
        )
    }
}