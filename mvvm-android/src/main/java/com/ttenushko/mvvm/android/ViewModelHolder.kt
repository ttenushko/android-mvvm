package com.ttenushko.mvvm.android

import com.ttenushko.mvvm.ViewModel
import androidx.lifecycle.ViewModel as AndroidViewModel


public abstract class ViewModelHolder<S : ViewModel.State, V : ViewModel<S>>(
    @Suppress("MemberVisibilityCanBePrivate") public val viewModel: V
) : AndroidViewModel() {

    override fun onCleared() {
        super.onCleared()
        viewModel.close()
    }
}