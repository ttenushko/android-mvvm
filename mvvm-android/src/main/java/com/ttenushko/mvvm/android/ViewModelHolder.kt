package com.ttenushko.mvvm.android

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import com.ttenushko.mvvm.ViewModel
import androidx.lifecycle.ViewModel as AndroidViewModel


@Suppress("MemberVisibilityCanBePrivate")
public abstract class ViewModelHolder<V : ViewModel<*, *>>(
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel() {

    public val viewModel: V by lazy {
        createViewModel(
            savedStateHandle.get<Bundle?>(SAVED_VIEW_MODEL_STATE)
                ?.let { if (!it.isEmpty) it else null })
    }


    init {
        savedStateHandle.setSavedStateProvider(SAVED_VIEW_MODEL_STATE) {
            saveViewModelState(viewModel) ?: Bundle()
        }
    }

    final override fun onCleared() {
        super.onCleared()
        viewModel.release()
    }

    protected abstract fun createViewModel(savedState: Bundle?): V

    protected abstract fun saveViewModelState(viewModel: V): Bundle?

    private companion object {
        private const val SAVED_VIEW_MODEL_STATE = "savedViewModelState"
    }
}