package com.ttenushko.mvvm.demo.domain.application.usecase

import com.ttenushko.mvvm.demo.domain.application.repository.ApplicationSettings
import com.ttenushko.mvvm.demo.domain.application.repository.trackPlacesUpdated
import com.ttenushko.mvvm.demo.domain.usecase.FlowMultiResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class TrackSavedPlacesUseCaseImpl(
    private val applicationSettings: ApplicationSettings,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowMultiResultUseCase<TrackSavedPlacesUseCase.Param, TrackSavedPlacesUseCase.Result>(
    ioDispatcher
), TrackSavedPlacesUseCase {

    override fun createFlow(param: TrackSavedPlacesUseCase.Param): Flow<TrackSavedPlacesUseCase.Result> =
        applicationSettings.trackPlacesUpdated()
            .onStart { emit(Unit) }
            .map { TrackSavedPlacesUseCase.Result(applicationSettings.getPlaces()) }
}