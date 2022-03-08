package com.ttenushko.mvvm.demo.presentation.screen.places

import com.ttenushko.mvvm.demo.domain.application.usecase.TrackSavedPlacesUseCase
import com.ttenushko.mvvm.demo.domain.weather.model.Place
import com.ttenushko.mvvm.demo.presentation.base.router.Router
import com.ttenushko.mvvm.demo.presentation.base.viewmodel.BaseViewModel
import com.ttenushko.mvvm.demo.presentation.screen.MainRouter
import com.ttenushko.mvvm.demo.presentation.screen.places.PlacesViewModel.ViewState
import com.ttenushko.mvvm.demo.presentation.utils.task.TaskExecutorFactory
import com.ttenushko.mvvm.demo.presentation.utils.task.asTask
import com.ttenushko.mvvm.demo.presentation.utils.task.createTaskExecutor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlacesViewModelImpl(
    savedState: PlacesViewModel.State?,
    private val trackSavedPlacesUseCase: TrackSavedPlacesUseCase,
    private val taskExecutorFactory: TaskExecutorFactory,
    private val router: Router<MainRouter.Destination>
) : BaseViewModel<PlacesViewModel.State>(), PlacesViewModel {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.NoContent)
    private val _isLoading = MutableStateFlow(false)
    private val taskTrackSavedPlaces = createTrackSavedPlacesTask()
    override val viewState: StateFlow<ViewState> = _viewState
    override val isLoading: StateFlow<Boolean> = _isLoading

    override fun saveState(): PlacesViewModel.State? =
        null

    override fun start() {
        super.start()
        taskTrackSavedPlaces.start(TrackSavedPlacesUseCase.Param, Unit)
    }

    override fun stop() {
        super.stop()
        taskTrackSavedPlaces.stop()
    }

    override fun addPlaceButtonClicked() {
        router.navigateTo(MainRouter.Destination.AddPlace(""))
    }

    override fun placeClicked(place: Place) {
        router.navigateTo(MainRouter.Destination.PlaceDetails(placeId = place.id))
    }

    private fun createTrackSavedPlacesTask() =
        taskExecutorFactory.createTaskExecutor<TrackSavedPlacesUseCase.Param, TrackSavedPlacesUseCase.Result, Unit>(
            task = trackSavedPlacesUseCase.asTask(),
            startHandler = { _ ->
                _isLoading.value = _viewState.value !is ViewState.HasContent
            },
            resultHandler = { result, _ ->
                _viewState.value = ViewState.HasContent(result.places)
                _isLoading.value = false
            },
            completeHandler = { _ ->
                _viewState.value = ViewState.NoContent
                _isLoading.value = false
            },
            errorHandler = { error, _ ->
                _viewState.value = ViewState.Error(error)
                _isLoading.value = false
            }
        )
}