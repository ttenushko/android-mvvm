package com.ttenushko.androidmvi.demo.presentation.screen.places.mvi

import com.ttenushko.androidmvi.demo.domain.weather.model.Place

internal sealed class Action {

    data class Intent(val intent: Store.Intent) : Action()

    object Initialize : Action() {
        override fun toString(): String =
            "Initialize"
    }

    data class SavedPlacesUpdated(val result: Result<List<Place>>) : Action()
}