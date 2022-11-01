package com.example.trans.utillity

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.trans.R
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class UtilsClassUI @Inject constructor(
    @ActivityContext private val context: Context,
) {

    fun onUpdateNeeded(
        isMandatoryUpdate: Boolean,
        onPositiveButtonClicked: () -> Unit,
        onNegativeButtonClicked: () -> Unit
    ) {
        val dialogBuilder = AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.update_app))
            .setCancelable(false)
            .setMessage(if (isMandatoryUpdate) context.getString(R.string.dialog_update_available_message) else "A new version is found on Play store, please update for better usage.")
            .setPositiveButton(context.getString(R.string.update_now))
            { _, _ ->
                onPositiveButtonClicked()
            }

        if (!isMandatoryUpdate) {
            dialogBuilder.setNegativeButton(context.getString(R.string.later)) { dialog, which ->
                onNegativeButtonClicked()
                dialog?.dismiss()
            }.create()
        }
        val dialog: AlertDialog = dialogBuilder.create()
        dialog.show()
    }
}