package com.ttenushko.mvvm.demo.data.weather.repository.rest

import com.ttenushko.mvvm.demo.data.weather.repository.rest.model.NetFindResponse
import com.ttenushko.mvvm.demo.data.weather.repository.rest.model.NetWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {
    @GET("find")
    fun find(@Query(value = "q") query: String): Call<NetFindResponse>

    @GET("weather")
    fun getWeather(
        @Query(value = "id") placeId: Long,
        @Query(value = "units") units: String = "metric"
    ): Call<NetWeather>
}