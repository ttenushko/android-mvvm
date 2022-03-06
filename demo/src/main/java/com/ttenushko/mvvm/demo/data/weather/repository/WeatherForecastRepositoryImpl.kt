package com.ttenushko.mvvm.demo.data.weather.repository

import com.ttenushko.mvvm.demo.data.weather.repository.rest.OpenWeatherMapApi
import com.ttenushko.mvvm.demo.data.weather.repository.rest.model.process
import com.ttenushko.mvvm.demo.data.weather.repository.rest.model.toDomainModel
import com.ttenushko.mvvm.demo.domain.weather.model.Place
import com.ttenushko.mvvm.demo.domain.weather.model.Weather
import com.ttenushko.mvvm.demo.domain.weather.repository.WeatherForecastRepository

class WeatherForecastRepositoryImpl(
    private val openWeatherMapApi: OpenWeatherMapApi
) : WeatherForecastRepository {

    override fun findPlace(query: String): List<Place> =
        openWeatherMapApi.find(query).process().let { response ->
            response.items.map { it.toDomainModel() }
        }

    override fun getWeather(placeId: Long): Weather =
        openWeatherMapApi.getWeather(placeId).process().toDomainModel()
}