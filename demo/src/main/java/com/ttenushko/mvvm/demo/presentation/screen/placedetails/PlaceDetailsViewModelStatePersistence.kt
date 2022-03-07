package com.ttenushko.mvvm.demo.presentation.screen.placedetails

import android.os.Bundle
import com.ttenushko.mvvm.android.ViewModelStatePersistence

class PlaceDetailsViewModelStatePersistence : ViewModelStatePersistence<PlaceDetailsViewModel.State> {

    override fun saveState(state: PlaceDetailsViewModel.State?, outState: Bundle) {
        // do nothing
    }

    override fun restoreState(savedState: Bundle?): PlaceDetailsViewModel.State? {
        return null
    }
}