package com.ttenushko.androidmvi.demo.presentation.screen.addplace.mvi

import com.ttenushko.androidmvi.demo.presentation.base.router.Router
import com.ttenushko.androidmvi.demo.presentation.screen.MainRouter
import com.ttenushko.mvi.extra.MviSimpleMiddleware

internal fun navigationMiddleware(router: Router<MainRouter.Destination>) =
    MviSimpleMiddleware<Action, Store.State, Store.Event> { action, chain ->
        when {
            action is Action.Intent && action.intent is Store.Intent.ToolbarBackButtonClicked -> {
                router.navigateTo(MainRouter.Destination.GoBack)
            }
            action is Action.PlaceSaved -> {
                router.navigateTo(MainRouter.Destination.GoBack)
            }
        }
        chain.proceed()
    }