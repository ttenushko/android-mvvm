package com.ttenushko.mvvm.android

import android.os.Bundle
import com.ttenushko.mvvm.ViewModel

public interface ViewModelStatePersistence<S : ViewModel.State> {
    public fun saveState(state: S?, outState: Bundle)
    public fun restoreState(savedState: Bundle?): S?
}