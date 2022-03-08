package com.ttenushko.mvvm.demo.presentation.screen.addplace

import com.ttenushko.mvvm.demo.domain.application.usecase.SavePlaceUseCase
import com.ttenushko.mvvm.demo.domain.utils.MutableObservableValue
import com.ttenushko.mvvm.demo.domain.weather.model.Place
import com.ttenushko.mvvm.demo.domain.weather.usecase.SearchPlaceUseCase
import com.ttenushko.mvvm.demo.presentation.base.router.Router
import com.ttenushko.mvvm.demo.presentation.base.viewmodel.BaseViewModel
import com.ttenushko.mvvm.demo.presentation.screen.MainRouter
import com.ttenushko.mvvm.demo.presentation.screen.addplace.AddPlaceViewModel.Event
import com.ttenushko.mvvm.demo.presentation.screen.addplace.AddPlaceViewModel.ViewState
import com.ttenushko.mvvm.demo.presentation.screen.addplace.AddPlaceViewModel.ViewState.SearchResult
import com.ttenushko.mvvm.demo.presentation.utils.task.TaskExecutorFactory
import com.ttenushko.mvvm.demo.presentation.utils.task.asTask
import com.ttenushko.mvvm.demo.presentation.utils.task.createTaskExecutor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class AddPlaceViewModelImpl(
    savedState: AddPlaceViewModel.State?,
    search: String,
    private val searchPlaceUseCase: SearchPlaceUseCase,
    private val savePlaceUseCase: SavePlaceUseCase,
    private val taskExecutorFactory: TaskExecutorFactory,
    private val router: Router<MainRouter.Destination>
) : BaseViewModel<AddPlaceViewModel.State>(), AddPlaceViewModel {

    private val _viewState = MutableStateFlow(
        ViewState(
            search = savedState?.search ?: search,
            searchResult = null
        )
    )

    private val _events = MutableSharedFlow<Event>()
    private val searchValue = MutableObservableValue("")
    private val taskSavePlace = createSavePlaceTask()
    private val taskSearchPlace = createSearchPlaceTask()
    override val viewState: StateFlow<ViewState> = _viewState
    override val events: SharedFlow<Event> = _events

    override fun saveState(): AddPlaceViewModel.State =
        AddPlaceViewModel.State(_viewState.value.search)

    override fun start() {
        super.start()
        searchValue.value = _viewState.value.search
        taskSearchPlace.start(SearchPlaceUseCase.Param(searchValue), Unit)
    }

    override fun stop() {
        super.stop()
        taskSearchPlace.stop()
        taskSavePlace.stop()
    }

    override fun searchChanged(search: String) {
        _viewState.value = _viewState.value.copy(search = search)
        searchValue.value = search
    }

    override fun placeClicked(place: Place) {
        if (!taskSavePlace.isRunning) {
            taskSavePlace.start(SavePlaceUseCase.Param(place), Unit)
        }
    }

    private fun createSearchPlaceTask() =
        taskExecutorFactory.createTaskExecutor<SearchPlaceUseCase.Param, SearchPlaceUseCase.Result, Unit>(
            task = searchPlaceUseCase.asTask(),
            resultHandler = { result, _ ->
                _viewState.value = _viewState.value.copy(
                    searchResult = when (result) {
                        is SearchPlaceUseCase.Result.Success -> SearchResult.Success(
                            result.search,
                            result.places
                        )
                        is SearchPlaceUseCase.Result.Failure -> SearchResult.Failure(
                            result.search,
                            result.error
                        )
                    }
                )
            },
            errorHandler = { error, _ ->
                _viewState.value = _viewState.value.let {
                    it.copy(searchResult = SearchResult.Failure(it.search, error))
                }
            }
        )

    private fun createSavePlaceTask() =
        taskExecutorFactory.createTaskExecutor<SavePlaceUseCase.Param, SavePlaceUseCase.Result, Unit>(
            task = savePlaceUseCase.asTask(),
            resultHandler = { _, _ ->
                router.navigateTo(MainRouter.Destination.GoBack)
            },
            errorHandler = { error, _ ->
                _events.tryEmit(Event.ShowErrorPopup(error))
            }
        )
}