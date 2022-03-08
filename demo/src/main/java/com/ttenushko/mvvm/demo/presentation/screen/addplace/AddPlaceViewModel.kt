package com.ttenushko.mvvm.demo.presentation.screen.addplace

import com.ttenushko.mvvm.demo.domain.weather.model.Place
import com.ttenushko.mvvm.demo.presentation.base.viewmodel.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AddPlaceViewModel : ViewModel<AddPlaceViewModel.State> {

    val viewState: StateFlow<ViewState>
    val events: SharedFlow<Event>

    fun searchChanged(search: String)
    fun placeClicked(place: Place)

    data class ViewState(
        val search: String,
        val searchResult: SearchResult?
    ) {
        val isSearching: Boolean
            get() {
                return search != searchResult?.search
            }
        val isShowSearchPrompt: Boolean =
            !isSearching && search.isBlank()

        val isShowSearchErrorPrompt =
            !isSearching && searchResult is SearchResult.Failure

        val isShowSearchNoResultsPrompt =
            !isSearching && search.isNotBlank() && searchResult is SearchResult.Success && searchResult.places.isEmpty()

        sealed class SearchResult(open val search: String) {
            data class Success(
                override val search: String,
                val places: List<Place>
            ) : SearchResult(search)

            data class Failure(
                override val search: String,
                val error: Throwable
            ) : SearchResult(search)
        }
    }

    data class State(val search: String) : ViewModel.State

    sealed class Event {
        data class ShowErrorPopup(val error: Throwable) : Event()
    }
}