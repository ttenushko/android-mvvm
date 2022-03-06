package com.ttenushko.androidmvi.demo.presentation.screen.placedetails.mvi

import com.ttenushko.androidmvi.demo.presentation.screen.placedetails.mvi.Store.State
import com.ttenushko.mvi.MviReducer

internal fun reducer() =
    MviReducer<Action, State> { action, state ->
        when (action) {
            is Action.Refreshing -> {
                state.copy(isRefreshing = true)
            }
            is Action.Refreshed -> {
                with(action.result) {
                    getOrNull()?.let { weather ->
                        state.copy(
                            weather = weather,
                            error = null,
                            isRefreshing = false
                        )
                    } ?: exceptionOrNull()?.let { error ->
                        state.copy(
                            weather = null,
                            error = error,
                            isRefreshing = false
                        )
                    } ?: state
                }
            }
            is Action.Deleting -> {
                state.copy(isDeleting = true)
            }
            is Action.Deleted -> {
                state.copy(isDeleting = false)
            }
            else -> state
        }
    }