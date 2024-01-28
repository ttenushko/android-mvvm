package com.ttenushko.mvvm.demo.presentation.base.fragment

import android.os.Bundle
import com.ttenushko.mvvm.demo.presentation.base.viewmodel.ViewModel

abstract class BaseMvvmFragment<S : ViewModel.State, V : ViewModel<S>> : BaseFragment() {

    private lateinit var _viewModel: V
    private lateinit var _viewModelStatePersistence: ViewModelStatePersistence<S>
    protected val viewModel: V
        get() = _viewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModelStatePersistence = provideViewModelStatePersistence()
        _viewModel = provideViewModel(_viewModelStatePersistence.restoreState(savedInstanceState))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        _viewModelStatePersistence.saveState(viewModel.saveState(), outState)
    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stop()
    }

    protected abstract fun provideViewModelStatePersistence(): ViewModelStatePersistence<S>

    protected abstract fun provideViewModel(savedState: S?): V
}
