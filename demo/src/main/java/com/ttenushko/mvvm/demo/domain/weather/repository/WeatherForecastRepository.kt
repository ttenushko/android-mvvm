package com.ttenushko.mvvm.demo.domain.weather.repository

import com.ttenushko.mvvm.demo.domain.weather.model.Place
import com.ttenushko.mvvm.demo.domain.weather.model.Weather

interface WeatherForecastRepository {
    fun findPlace(query: String): List<Place>
    fun getWeather(placeId: Long): Weather
}