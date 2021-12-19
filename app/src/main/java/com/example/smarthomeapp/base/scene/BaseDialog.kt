package com.example.smarthomeapp.base.scene

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentActivity
import timber.log.Timber

open class BaseDialog : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.run {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun show(activity: FragmentActivity?, tag: String) {
        activity?.let {
            if (!isAdded) {
                try {
                    show(it.supportFragmentManager, tag)
                } catch (ex: IllegalStateException) {
                    Timber.e("show Dialog get IllegalStateException act[${it.javaClass.simpleName}] tag[${tag}] class[${javaClass.simpleName}]")
                    ex.printStackTrace()
                }
            }
        }
    }

    open val isShow: Boolean
        get() = dialog?.isShowing ?: false

    companion object {
        fun dismissIfShowing(
            activity: FragmentActivity,
            tag: String?
        ) {
            activity.supportFragmentManager.findFragmentByTag(tag)?.run {
                if (this is BaseDialog && isShow) dismissAllowingStateLoss()
            }
        }
    }
}