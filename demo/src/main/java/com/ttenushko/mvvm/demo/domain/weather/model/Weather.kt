package com.ttenushko.mvvm.demo.domain.weather.model

data class Weather(
    val place: Place,
    val conditions: WeatherConditions,
    val descriptions: List<WeatherDescription>
)