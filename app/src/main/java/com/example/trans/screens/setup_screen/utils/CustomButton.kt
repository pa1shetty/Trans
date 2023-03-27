package com.example.trans.screens.setup_screen.utils

import android.app.Activity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.trans.R
import com.example.trans.screens.setup_screen.utils.LoginScreenEnum.ButtonStatusEnum

class CustomButton(
    private val cvProceed: CardView,
    private val pbLogin: ProgressBar,
    private val activity: Activity,
    private val ivLogin: ImageView? = null,
    private val tvProceed: TextView? = null,
) {
    fun enable() {
        loginButtonStatus(
            ButtonStatusEnum.ENABLE.status,
        )
    }

    fun disable() {
        loginButtonStatus(
            ButtonStatusEnum.DISABLE.status,
        )
    }

    fun startLoading() {
        loginButtonStatus(
            ButtonStatusEnum.LOADING.status,
        )

    }

    fun stopLoading() {
        loginButtonStatus(
            ButtonStatusEnum.NOT_LOADING.status,
        )
    }


    private fun loginButtonStatus(
        status: Int
    ) {
        when (status) {
            ButtonStatusEnum.ENABLE.status -> {
                cvProceed.isEnabled = true
                cvProceed.setCardBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.bmrcl_color
                    )
                )
                ivLogin?.setColorFilter(
                    ContextCompat.getColor(
                        activity,
                        R.color.bmrcl_color_bg
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                tvProceed?.setTextColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.white
                    )
                )
            }
            ButtonStatusEnum.DISABLE.status -> {
                cvProceed.isEnabled = false
                cvProceed.setCardBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.bmrcl_color_bg
                    )
                )
                ivLogin?.setColorFilter(
                    ContextCompat.getColor(
                        activity,
                        R.color.lightGray
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                tvProceed?.setTextColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.lightGray
                    )
                )

            }
            ButtonStatusEnum.LOADING.status -> {
                cvProceed.isEnabled = false
                pbLogin.visibility = VISIBLE
                ivLogin?.let {
                    it.visibility = GONE
                }
                tvProceed?.let {
                    it.visibility = GONE
                }
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            }
            else -> {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                cvProceed.isEnabled = true
                pbLogin.visibility = GONE
                ivLogin?.let {
                    it.visibility = VISIBLE
                }
                tvProceed?.let {
                    it.visibility = VISIBLE
                }
            }
        }
    }
}

@Suppress("unused")
class LoginScreenEnum {
    enum class ButtonStatusEnum(val status: Int) {
        ENABLE(0),
        DISABLE(1),
        LOADING(2),
        NOT_LOADING(3),
    }
}