package com.ttenushko.mvvm.demo.di.data

import android.content.Context
import com.ttenushko.mvvm.demo.Config
import com.ttenushko.mvvm.demo.data.application.repository.ApplicationSettingsImpl
import com.ttenushko.mvvm.demo.data.weather.repository.WeatherForecastRepositoryImpl
import com.ttenushko.mvvm.demo.data.weather.repository.rest.OpenWeatherMapApi
import com.ttenushko.mvvm.demo.data.weather.repository.rest.OpenWeatherMapApiFactory
import com.ttenushko.mvvm.demo.domain.application.repository.ApplicationSettings
import com.ttenushko.mvvm.demo.domain.weather.repository.WeatherForecastRepository
import com.ttenushko.mvvm.demo.presentation.utils.dagger.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DataModule(
    private val isDebug: Boolean
) {
    @Provides
    @ApplicationScope
    fun provideApplicationSettings(context: Context): ApplicationSettings =
        ApplicationSettingsImpl(
            context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        )

    @Provides
    @ApplicationScope
    fun provideOpenWeatherMapApi(context: Context): OpenWeatherMapApi =
        OpenWeatherMapApiFactory.create(
            context,
            Config.OPEN_WEATHER_MAP_API_BASE_URL,
            Config.OPEN_WEATHER_MAP_API_KEY,
            isDebug
        )

    @Provides
    @ApplicationScope
    fun provideWeatherForecastRepository(openWeatherMapApi: OpenWeatherMapApi): WeatherForecastRepository =
        WeatherForecastRepositoryImpl(openWeatherMapApi)
}