package com.ttenushko.androidmvi.demo.presentation.screen.addplace.mvi

import com.ttenushko.androidmvi.demo.presentation.screen.addplace.mvi.Store.Intent
import com.ttenushko.androidmvi.demo.presentation.screen.addplace.mvi.Store.State
import com.ttenushko.mvi.MviReducer


internal fun reducer() =
    MviReducer<Action, State> { action, state ->
        when {
            action is Action.Intent && action.intent is Intent.SearchChanged -> {
                if (state.search != action.intent.search) {
                    state.copy(search = action.intent.search)
                } else state
            }
            action is Action.SearchComplete -> {
                state.copy(searchResult = action.searchResult)
            }
            else -> state
        }
    }