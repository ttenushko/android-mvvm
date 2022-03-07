package com.ttenushko.mvvm.demo.presentation.utils.task

import com.ttenushko.mvvm.demo.domain.usecase.Cancellable

interface Task<P : Any, R : Any> {
    fun execute(param: P, callback: Callback<R>): Cancellable

    interface Callback<R : Any> {
        fun onResult(result: R)
        fun onComplete()
        fun onError(error: Throwable)
    }
}