package com.ttenushko.mvvm

import java.util.concurrent.atomic.AtomicBoolean


public class ReleaseHandler(private val releaseHandler: () -> Unit) : Releasable {

    public val isReleased: Boolean
        get() = _isReleased.get()
    private val _isReleased = AtomicBoolean(false)

    public fun checkNotReleased() {
        if (_isReleased.get()) {
            throw IllegalStateException("This instance is released.")
        }
    }

    override fun release() {
        if (!_isReleased.getAndSet(true)) {
            releaseHandler()
        }
    }
}