package com.ttenushko.mvvm.demo.presentation.base.viewmodel

import com.ttenushko.mvvm.BaseViewModel as CoreBaseViewModel

abstract class BaseViewModel<S : ViewModel.State> : CoreBaseViewModel<S>(), ViewModel<S> {

    override fun start() {
        // do nothing
    }

    override fun stop() {
        // do nothing
    }
}