package com.ttenushko.mvvm.demo.presentation.screen.addplace

import android.os.Bundle
import com.ttenushko.mvvm.android.ViewModelStatePersistence

class AddPlaceViewModelStatePersistence : ViewModelStatePersistence<AddPlaceViewModel.State> {

    override fun saveState(state: AddPlaceViewModel.State?, outState: Bundle) {
        // do nothing
    }

    override fun restoreState(savedState: Bundle?): AddPlaceViewModel.State? {
        return null
    }
}