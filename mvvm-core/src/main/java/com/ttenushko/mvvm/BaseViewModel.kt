package com.ttenushko.mvvm

public abstract class BaseViewModel<S : ViewModel.State> : ViewModel<S> {

    protected val isClosed: Boolean
        get() = closeHandler.isClosed
    private val closeHandler = CloseHandler {
        onClose()
    }

    final override fun close() {
        closeHandler.closeHandler
    }

    protected fun checkNotClosed() {
        closeHandler.checkNotClosed()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun onClose() {
        // do nothing
    }
}