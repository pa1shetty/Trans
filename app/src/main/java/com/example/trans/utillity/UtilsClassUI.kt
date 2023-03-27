package com.example.trans.utillity

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
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
            dialogBuilder.setNegativeButton(context.getString(R.string.later)) { dialog, _ ->
                onNegativeButtonClicked()
                dialog?.dismiss()
            }.create()
        }
        val dialog: AlertDialog = dialogBuilder.create()
        dialog.show()
    }

    fun toastMessage(message: String = context.getString(R.string.something_went_wrong)) {
        Toast.makeText(context, message, Toast.LENGTH_LONG)
            .show()
    }

    fun showAlertDialog(
        title: String = "Access Denied",
        message: String = "You are not allowed to use this app, Please connect with the team"
    ) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(
            "Ok"
        ) { _, _ ->
            //Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
//        alertDialog.setNegativeButton(
//            "No"
//        ) { _, _ -> }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    fun showLoadingUI(loadingLayout: View) {
        loadingLayout.visibility = View.VISIBLE
        //not working
        val rootView: View =
            (context as Activity).window.decorView.findViewById(android.R.id.content)
        rootView.isEnabled = false
    }

    fun hideLoadingUI(loadingLayout: View) {
        loadingLayout.visibility = View.GONE
        //not working
        val rootView: View =
            (context as Activity).window.decorView.findViewById(android.R.id.content)
        rootView.isEnabled = true
    }
}