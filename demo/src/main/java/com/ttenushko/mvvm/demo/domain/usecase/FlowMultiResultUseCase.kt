package com.ttenushko.mvvm.demo.domain.usecase

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

abstract class FlowMultiResultUseCase<P : Any, R : Any>(
    private val dispatcher: CoroutineDispatcher
) : MultiResultUseCase<P, R> {

    final override fun execute(param: P, callback: MultiResultUseCase.Callback<R>): Cancellable =
        CoroutineScope(dispatcher + Job()).let { coroutineScope ->
            coroutineScope.launch {
                try {
                    createFlow(param)
                        .collect { callback.onResult(it) }
                    callback.onComplete()
                } catch (error: Throwable) {
                    callback.onError(error)
                }
            }.also { job ->
                job.invokeOnCompletion { coroutineScope.cancel() }
            }
            coroutineScope.asCancellable()
        }

    protected abstract fun createFlow(param: P): Flow<R>
}