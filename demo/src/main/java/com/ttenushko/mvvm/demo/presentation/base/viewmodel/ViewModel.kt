package com.ttenushko.mvvm.demo.presentation.base.viewmodel

import com.ttenushko.mvvm.ViewModel as CoreViewModel

interface ViewModel<S : ViewModel.State> : CoreViewModel<S> {

    fun start()

    fun stop()

    interface State: CoreViewModel.State
}