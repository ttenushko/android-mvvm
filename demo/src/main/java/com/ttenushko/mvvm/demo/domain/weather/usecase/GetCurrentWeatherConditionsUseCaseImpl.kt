package com.ttenushko.mvvm.demo.domain.weather.usecase

import com.ttenushko.mvvm.demo.domain.usecase.CoroutineSingleResultUseCase
import com.ttenushko.mvvm.demo.domain.weather.repository.WeatherForecastRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetCurrentWeatherConditionsUseCaseImpl(
    private val weatherForecastRepository: WeatherForecastRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetCurrentWeatherConditionsUseCase.Param, GetCurrentWeatherConditionsUseCase.Result>(
    ioDispatcher
), GetCurrentWeatherConditionsUseCase {

    override suspend fun run(param: GetCurrentWeatherConditionsUseCase.Param): GetCurrentWeatherConditionsUseCase.Result =
        GetCurrentWeatherConditionsUseCase.Result(weatherForecastRepository.getWeather(param.placeId))
}