package com.ttenushko.mvvm.demo.di

import android.content.Context
import com.squareup.picasso.Picasso
import com.ttenushko.mvvm.demo.domain.application.repository.ApplicationSettings
import com.ttenushko.mvvm.demo.domain.weather.repository.WeatherForecastRepository
import com.ttenushko.mvvm.demo.presentation.base.router.RouterProxy
import com.ttenushko.mvvm.demo.presentation.screen.MainRouter
import com.ttenushko.mvvm.demo.presentation.utils.dagger.ApplicationContext


interface PublicDependencies : Dependency {
    fun applicationSettings(): ApplicationSettings
    fun weatherForecastRepository(): WeatherForecastRepository
    fun mainRouterProxy(): RouterProxy<MainRouter.Destination>
    fun picasso(): Picasso

    @ApplicationContext
    fun context(): Context
}