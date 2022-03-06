package com.ttenushko.mvvm.demo.presentation.base.dialog

import android.content.DialogInterface
import androidx.fragment.app.DialogFragment


interface DialogFragmentClickListener {
    fun onDialogFragmentClick(dialogFragment: DialogFragment, dialog: DialogInterface, which: Int)
}
