package com.example.smarthomeapp.base.dialog

import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

@Suppress("unused")
class ConfirmBuilder(val resources: Resources) {

    var cancelable = true

    var title: String? = null
    var message: CharSequence? = null
    var positive: String? = null
    var negative: String? = null

    @DrawableRes
    var icon = 0

    var onPositiveClickListener: (() -> Unit)? = null
    var onNegativeClickListener: (() -> Unit)? = null
    var onCancelListener: (() -> Unit)? = null
    var onShowListener: (() -> Unit)? = null
    var onDismissListener: (() -> Unit)? = null

    /**
     * Set dialog title
     *
     * @param title the string title, or null to hide
     * @return this builder
     */
    fun setTitle(title: String?): ConfirmBuilder {
        this.title = title
        return this
    }

    /**
     * Set dialog message
     *
     * @param message the string message
     * @return this builder
     */
    fun setMessage(message: CharSequence?): ConfirmBuilder {
        this.message = message
        return this
    }

    /**
     * Convenient method for set string title from resource
     *
     * @param title the title string resid
     * @return this builder
     * @see setTitle
     */
    fun setTitle(@StringRes title: Int): ConfirmBuilder {
        this.title = resources.getString(title)
        return this
    }

    /**
     * Convenient method for set icon from resource
     *
     * @param icon the drawble icon resid
     * @return this builder
     */
    fun setIcon(@DrawableRes icon: Int): ConfirmBuilder {
        this.icon = icon
        return this
    }

    /**
     * Convenient method for set string message from resource
     *
     * @param message the message string resid
     * @return this builder
     * @see Builder.setMessage
     */
    fun setMessage(@StringRes message: Int): ConfirmBuilder {
        this.message = resources.getString(message)
        return this
    }

    /**
     * Setup positive button
     *
     * @param positive string for positive action, null to hide
     * @param callback to be invoked when positive button clicked
     * @return this builder
     */
    fun setPositive(
        positive: String?,
        callback: (() -> Unit)?
    ): ConfirmBuilder {
        this.positive = positive
        onPositiveClickListener = callback
        return this
    }

    /**
     * Setup negative button
     *
     * @param negative string for negative action, null to hide
     * @param callback to be invoked when negative button clicked
     * @return this builder
     */
    fun setNegative(
        negative: String?,
        callback: (() -> Unit)?
    ): ConfirmBuilder {
        this.negative = negative
        this.onNegativeClickListener = callback
        return this
    }

    /**
     * Convenient method for setup positive button from string res
     *
     * @param positive string resid for positive action
     * @param callback to be invoked when positive button clicked
     * @return this builder
     * @see setPositive
     */
    fun setPositive(
        @StringRes positive: Int,
        callback: (() -> Unit)?
    ): ConfirmBuilder {
        this.positive = resources.getString(positive)
        onPositiveClickListener = callback
        return this
    }

    /**
     * Convenient method for setup negative button from string res
     *
     * @param negative string resid for positive action
     * @param callback to be invoked when negative button clicked
     * @return this builder
     * @see setNegative
     */
    fun setNegative(
        @StringRes negative: Int,
        callback: (() -> Unit)?
    ): ConfirmBuilder {
        this.negative = resources.getString(negative)
        this.onNegativeClickListener = callback
        return this
    }

    /**
     * @return this builder
     * @see DialogFragment.setCancelable
     */
    fun setCancelable(cancelable: Boolean): ConfirmBuilder {
        this.cancelable = cancelable
        return this
    }

    /**
     * @return this builder
     * @see Dialog.setOnCancelListener
     */
    fun setOnCancelListener(onCancelListener: (() -> Unit)?): ConfirmBuilder {
        this.onCancelListener = onCancelListener
        return this
    }

    /**
     * @return this builder
     * @see Dialog.setOnShowListener
     */
    fun setOnShowListener(onShowListener: (() -> Unit)?): ConfirmBuilder {
        this.onShowListener = onShowListener
        return this
    }

    /**
     * @return this builder
     * @see Dialog.setOnDismissListener
     */
    fun setOnDismissListener(onDismissListener: (() -> Unit)?): ConfirmBuilder {
        this.onDismissListener = onDismissListener
        return this
    }
}