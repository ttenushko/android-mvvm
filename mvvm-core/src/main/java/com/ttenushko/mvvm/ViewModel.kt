package com.ttenushko.mvvm

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.StateFlow

public interface ViewModel<S, E> :
    Releasable {

    public val state: StateFlow<S>
    public val events: ReceiveChannel<E>
}