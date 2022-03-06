package com.ttenushko.androidmvi.demo.presentation.screen.places.mvi

import com.ttenushko.androidmvi.demo.presentation.screen.places.mvi.Store.State
import com.ttenushko.mvi.MviReducer

internal fun reducer() =
    MviReducer<Action, State> { action, state ->
        when (action) {
            is Action.SavedPlacesUpdated -> {
                with(action.result) {
                    getOrNull()?.let { state.copy(places = it, error = null) }
                        ?: exceptionOrNull()?.let { state.copy(places = null, error = it) }
                        ?: state
                }
            }
            else -> state
        }
    }