package com.ttenushko.mvvm.demo.domain.usecase

interface SingleResultUseCase<P : Any, R : Any> {

    fun execute(param: P, callback: Callback<R>): Cancellable

    interface Callback<R : Any> {
        fun onComplete(result: R)
        fun onError(error: Throwable)
    }
}