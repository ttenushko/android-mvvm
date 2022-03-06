package com.ttenushko.androidmvi.demo.presentation.screen.placedetails.mvi

import com.ttenushko.androidmvi.demo.presentation.base.router.Router
import com.ttenushko.androidmvi.demo.presentation.screen.MainRouter
import com.ttenushko.androidmvi.demo.presentation.screen.placedetails.mvi.Store.Event
import com.ttenushko.androidmvi.demo.presentation.screen.placedetails.mvi.Store.Intent
import com.ttenushko.mvi.extra.MviSimpleMiddleware

internal fun eventProducerMiddleware(router: Router<MainRouter.Destination>) =
    MviSimpleMiddleware<Action, Store.State, Event> { action, chain ->
        when {
            action is Action.Intent && action.intent is Intent.DeleteClicked -> {
                dispatchEvent(Event.ShowDeleteConfirmation)
            }
            action is Action.Intent && action.intent is Intent.ToolbarBackButtonClicked -> {
                router.navigateTo(MainRouter.Destination.GoBack)
            }
            action is Action.Refreshed -> {
                chain.proceed()
                action.result
                    .onFailure { error ->
                        dispatchEvent(Event.ShowError(error))
                    }
            }
            action is Action.Deleted -> {
                chain.proceed()
                action.result
                    .onSuccess {
                        router.navigateTo(MainRouter.Destination.GoBack)
                    }
                    .onFailure { error ->
                        dispatchEvent(Event.ShowError(error))
                    }
            }
            else -> chain.proceed()
        }
    }