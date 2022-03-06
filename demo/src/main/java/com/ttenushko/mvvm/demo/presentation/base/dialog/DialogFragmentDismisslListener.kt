package com.ttenushko.mvvm.demo.presentation.base.dialog

import android.content.DialogInterface

interface DialogFragmentDismissListener {
    fun onDialogFragmentDismiss(dialogFragment: BaseDialogFragment, dialog: DialogInterface)
}
