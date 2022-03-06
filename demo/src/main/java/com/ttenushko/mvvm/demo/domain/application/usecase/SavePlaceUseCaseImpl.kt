package com.ttenushko.mvvm.demo.domain.application.usecase

import com.ttenushko.mvvm.demo.domain.application.repository.ApplicationSettings
import com.ttenushko.mvvm.demo.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SavePlaceUseCaseImpl(
    private val applicationSettings: ApplicationSettings,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<SavePlaceUseCase.Param, SavePlaceUseCase.Result>(ioDispatcher),
    SavePlaceUseCase {

    override suspend fun run(param: SavePlaceUseCase.Param): SavePlaceUseCase.Result =
        applicationSettings.getPlaces().let { savedPlaces ->
            if (!savedPlaces.contains(param.place)) {
                applicationSettings.setPlaces(savedPlaces.plus(param.place))
                SavePlaceUseCase.Result(param.place, true)
            } else {
                SavePlaceUseCase.Result(param.place, false)
            }
        }
}