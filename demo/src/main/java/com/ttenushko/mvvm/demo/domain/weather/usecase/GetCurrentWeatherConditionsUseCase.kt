package com.ttenushko.mvvm.demo.domain.weather.usecase

import com.ttenushko.mvvm.demo.domain.usecase.SingleResultUseCase
import com.ttenushko.mvvm.demo.domain.weather.model.Weather

interface GetCurrentWeatherConditionsUseCase :
    SingleResultUseCase<GetCurrentWeatherConditionsUseCase.Param, GetCurrentWeatherConditionsUseCase.Result> {

    data class Param(
        val placeId: Long
    )

    data class Result(val weather: Weather)
}