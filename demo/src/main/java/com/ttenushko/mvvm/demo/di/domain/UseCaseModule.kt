package com.ttenushko.mvvm.demo.di.domain

import com.ttenushko.mvvm.demo.domain.application.repository.ApplicationSettings
import com.ttenushko.mvvm.demo.domain.application.usecase.*
import com.ttenushko.mvvm.demo.domain.weather.repository.WeatherForecastRepository
import com.ttenushko.mvvm.demo.domain.weather.usecase.GetCurrentWeatherConditionsUseCase
import com.ttenushko.mvvm.demo.domain.weather.usecase.GetCurrentWeatherConditionsUseCaseImpl
import com.ttenushko.mvvm.demo.domain.weather.usecase.SearchPlaceUseCase
import com.ttenushko.mvvm.demo.domain.weather.usecase.SearchPlaceUseCaseImpl
import com.ttenushko.mvvm.demo.presentation.utils.task.TaskExecutorFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
class UseCaseModule {

    @Provides
    fun provideDeletePlaceUseCase(applicationSettings: ApplicationSettings): DeletePlaceUseCase =
        DeletePlaceUseCaseImpl(applicationSettings)

    @Provides
    fun provideGetSavedPlacesUseCase(applicationSettings: ApplicationSettings): GetSavedPlacesUseCase =
        GetSavedPlacesUseCaseImpl(applicationSettings)

    @Provides
    fun provideSavePlaceUseCase(applicationSettings: ApplicationSettings): SavePlaceUseCase =
        SavePlaceUseCaseImpl(applicationSettings)

    @Provides
    fun provideTrackSavedPlacesUseCase(applicationSettings: ApplicationSettings): TrackSavedPlacesUseCase =
        TrackSavedPlacesUseCaseImpl(applicationSettings)

    @Provides
    fun provideGetCurrentWeatherConditionsUseCase(weatherForecastRepository: WeatherForecastRepository): GetCurrentWeatherConditionsUseCase =
        GetCurrentWeatherConditionsUseCaseImpl(weatherForecastRepository)

    @Provides
    fun provideSearchPlaceUseCase(weatherForecastRepository: WeatherForecastRepository): SearchPlaceUseCase =
        SearchPlaceUseCaseImpl(weatherForecastRepository, 300)
}