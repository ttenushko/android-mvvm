package com.ttenushko.mvvm

import java.io.Closeable

public interface ViewModel<S: ViewModel.State> : Closeable {

    public fun saveState(): S?

    public interface State
}