package com.ttenushko.mvvm

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@Suppress("MemberVisibilityCanBePrivate")
public abstract class BaseViewModel<S, E>(
    initialState: S,
) : ViewModel<S, E> {

    override val state: StateFlow<S>
        get() {
            return _state
        }
    override val events: ReceiveChannel<E>
        get() {
            return _events
        }
    protected val isReleased: Boolean
        get() {
            return releaseHandler.isReleased
        }
    private val _state = MutableStateFlow(initialState)
    private val _events = Channel<E>(capacity = Channel.UNLIMITED)

    private val releaseHandler = ReleaseHandler {
        onRelease()
    }

    override fun release() {
        releaseHandler.release()
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    protected inline fun updateState(function: (S) -> S) {
        _state.update(function)
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE", "NOTHING_TO_INLINE")
    protected inline fun sendEvent(event: E) {
        _events.trySend(event)
    }

    protected fun checkNotReleased() {
        releaseHandler.checkNotReleased()
    }

    protected fun onRelease() {
        // do nothing
    }
}