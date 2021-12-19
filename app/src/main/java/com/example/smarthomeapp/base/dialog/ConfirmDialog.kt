package com.example.smarthomeapp.base.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.scene.BaseDialog
import com.example.smarthomeapp.databinding.DiagConfirmBinding
import com.example.smarthomeapp.util.bus.Events.postAsEvent

class ConfirmDialog() : BaseDialog() {

    constructor(builder: ConfirmBuilder) : this() {
        title = builder.title
        message = builder.message
        icon = builder.icon
        negative = builder.negative
        positive = builder.positive
        flagCancelable = builder.cancelable

        onNegativeClickListener = builder.onNegativeClickListener
        onCancelListener = builder.onCancelListener
        onDismissListener = builder.onDismissListener
        onPositiveClickListener = builder.onPositiveClickListener
        onShowListener = builder.onShowListener
    }

    val TAG = "ConfirmDialog"

    private var binding: DiagConfirmBinding? = null

    private var title: String? = null
    private var message: CharSequence? = null
    private var positive: String? = null
    private var negative: String? = null

    private var flagCancelable = false

    @DrawableRes
    private var icon = 0

    private var onPositiveClickListener: (() -> Unit)? = null
    private var onNegativeClickListener: (() -> Unit)? = null
    private var onCancelListener: (() -> Unit)? = null
    private var onShowListener: (() -> Unit)? = null
    private var onDismissListener: (() -> Unit)? = null

    fun show(activity: FragmentActivity?) {
        super.show(activity, TAG)
        ConfirmDialogVisibilityChanged(true).postAsEvent()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { onShowListener?.invoke() }
        return dialog
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancelListener?.invoke()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke()
        ConfirmDialogVisibilityChanged(false).postAsEvent()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<DiagConfirmBinding>(
            inflater,
            R.layout.diag_confirm,
            container,
            false
        ).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = flagCancelable
        binding?.apply {
            btnClose.visibility = if (flagCancelable) View.VISIBLE else View.GONE
            if (flagCancelable) {
                btnClose.setOnClickListener {
                    dialog?.cancel()
                }
            }
            if (!TextUtils.isEmpty(this@ConfirmDialog.title)) {
                title.text = this@ConfirmDialog.title
            } else {
                title.visibility = View.GONE
            }
            message.text = this@ConfirmDialog.message
            if (!TextUtils.isEmpty(positive)) {
                btnPositive.apply {
                    text = positive
                    setOnClickListener {
                        onPositiveClickListener?.invoke()
                        dismissAllowingStateLoss()
                    }
                }
            } else {
                btnPositive.visibility = View.GONE
            }
            if (!TextUtils.isEmpty(negative)) {
                btnNegative.apply {
                    text = negative
                    visibility = View.VISIBLE
                    setOnClickListener {
                        onNegativeClickListener?.invoke()
                        dismissAllowingStateLoss()
                    }
                }
            } else {
                btnNegative.visibility = View.GONE
            }
            if (icon != 0) {
                imgIcon.setImageResource(icon)
            }
        }
    }
}

data class ConfirmDialogVisibilityChanged(val isShow: Boolean)