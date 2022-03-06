package com.ttenushko.androidmvi.demo.presentation.screen.places.mvi

import com.ttenushko.androidmvi.demo.presentation.base.router.Router
import com.ttenushko.androidmvi.demo.presentation.screen.MainRouter
import com.ttenushko.androidmvi.demo.presentation.screen.places.mvi.Store.Event
import com.ttenushko.androidmvi.demo.presentation.screen.places.mvi.Store.State
import com.ttenushko.mvi.extra.MviSimpleMiddleware

internal fun navigationMiddleware(router: Router<MainRouter.Destination>) =
    MviSimpleMiddleware<Action, State, Event> { action, chain ->
        if (action is Action.Intent) {
            when (action.intent) {
                is Store.Intent.AddPlaceButtonClicked -> {
                    router.navigateTo(MainRouter.Destination.AddPlace(""))
                }
                is Store.Intent.PlaceClicked -> {
                    router.navigateTo(MainRouter.Destination.PlaceDetails(action.intent.place.id))
                }
            }
        } else {
            chain.proceed()
        }
    }