package com.ttenushko.androidmvi.demo.presentation.screen.places.mvi

import com.ttenushko.androidmvi.demo.domain.application.usecase.TrackSavedPlacesUseCase
import com.ttenushko.androidmvi.demo.presentation.screen.places.mvi.Store.Event
import com.ttenushko.androidmvi.demo.presentation.screen.places.mvi.Store.State
import com.ttenushko.androidmvi.demo.presentation.utils.TaskMiddleware
import com.ttenushko.androidmvi.demo.presentation.utils.task.TaskExecutorFactory
import com.ttenushko.androidmvi.demo.presentation.utils.task.asTask

internal fun trackSavedPlacesMiddleware(
    trackSavedPlacesUseCase: TrackSavedPlacesUseCase,
    taskExecutorFactory: TaskExecutorFactory
) =
    TaskMiddleware<Action, State, Event, TrackSavedPlacesUseCase.Param, TrackSavedPlacesUseCase.Result, Unit>(
        task = trackSavedPlacesUseCase.asTask(),
        taskExecutorFactory = taskExecutorFactory,
        resultHandler = { result, _ ->
            dispatchAction(Action.SavedPlacesUpdated(Result.success(result.places)))
        },
        errorHandler = { error, _ ->
            dispatchAction(Action.SavedPlacesUpdated(Result.failure(error)))
        }
    ) { action, chain, task ->
        when (action) {
            is Action.Initialize -> {
                if (!task.isRunning) {
                    task.start(TrackSavedPlacesUseCase.Param, Unit)
                }
            }
            else -> {
                // do nothing
            }
        }
        chain.proceed()
    }