package com.ttenushko.mvvm.demo.presentation.screen.addplace

import android.os.Bundle
import com.ttenushko.mvvm.android.ViewModelStatePersistence

class AddPlaceViewModelStatePersistence : ViewModelStatePersistence<AddPlaceViewModel.State> {

    override fun saveState(state: AddPlaceViewModel.State?, outState: Bundle) {
        state?.let {
            outState.putString(SEARCH, it.search)
        }
    }

    override fun restoreState(savedState: Bundle?): AddPlaceViewModel.State? =
        savedState?.let {
            AddPlaceViewModel.State(it.getString(SEARCH, ""))
        }

    companion object {
        private const val SEARCH = "search"
    }
}