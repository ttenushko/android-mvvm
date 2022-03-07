package com.ttenushko.mvvm.demo.presentation.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified T : ViewModel> Fragment.viewModel(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    crossinline provider: () -> T
): Lazy<T> = viewModels(ownerProducer) {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return provider.invoke() as T
        }
    }
}