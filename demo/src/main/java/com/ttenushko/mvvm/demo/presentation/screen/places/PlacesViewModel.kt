package com.ttenushko.mvvm.demo.presentation.screen.places

import com.ttenushko.mvvm.demo.domain.weather.model.Place
import com.ttenushko.mvvm.demo.presentation.base.viewmodel.ViewModel
import kotlinx.coroutines.flow.StateFlow

interface PlacesViewModel : ViewModel<PlacesViewModel.State> {

    val viewState: StateFlow<ViewState>
    val isLoading: StateFlow<Boolean>

    fun addPlaceButtonClicked()
    fun placeClicked(place: Place)

    object State : ViewModel.State

    sealed class ViewState {
        object NoContent : ViewState()
        data class HasContent(val places: List<Place>) : ViewState()
        data class Error(val error: Throwable) : ViewState()
    }
}