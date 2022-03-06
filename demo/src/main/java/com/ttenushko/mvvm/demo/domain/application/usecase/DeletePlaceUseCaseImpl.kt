package com.ttenushko.mvvm.demo.domain.application.usecase

import com.ttenushko.mvvm.demo.domain.application.repository.ApplicationSettings
import com.ttenushko.mvvm.demo.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeletePlaceUseCaseImpl(
    private val applicationSettings: ApplicationSettings,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<DeletePlaceUseCase.Param, DeletePlaceUseCase.Result>(ioDispatcher),
    DeletePlaceUseCase {

    override suspend fun run(param: DeletePlaceUseCase.Param): DeletePlaceUseCase.Result {
        val savedPlaces = applicationSettings.getPlaces().filter { it.id != param.placeId }
        applicationSettings.setPlaces(savedPlaces)
        return DeletePlaceUseCase.Result(true)
    }
}