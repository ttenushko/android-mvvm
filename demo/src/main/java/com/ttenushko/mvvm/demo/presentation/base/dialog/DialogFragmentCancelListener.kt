package com.ttenushko.mvvm.demo.presentation.base.dialog

import android.content.DialogInterface
import androidx.fragment.app.DialogFragment


interface DialogFragmentCancelListener {
    fun onDialogFragmentCancel(dialogFragment: DialogFragment, dialog: DialogInterface)
}