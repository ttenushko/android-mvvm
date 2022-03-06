package com.ttenushko.mvvm.demo.presentation.base.fragment

import android.os.Bundle
import com.ttenushko.mvvm.ViewModel
import com.ttenushko.mvvm.android.ViewModelHolder
import com.ttenushko.mvvm.android.ViewModelStatePersistence

abstract class BaseMvvmFragment<S : ViewModel.State, V : ViewModel<S>> : BaseFragment() {

    private lateinit var viewModelHolder: ViewModelHolder<S, V>
    protected val viewModel: V
        get() = viewModelHolder.viewModel
    protected abstract val viewModelStatePersistence: ViewModelStatePersistence<S>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelHolder =
            provideViewModelHolder(viewModelStatePersistence.restoreState(savedInstanceState))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModelStatePersistence.saveState(viewModel.saveState(), outState)
    }

    protected abstract fun provideViewModelHolder(savedState: S?): ViewModelHolder<S, V>
}
