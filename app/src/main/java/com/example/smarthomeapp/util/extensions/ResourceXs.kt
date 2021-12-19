package com.example.smarthomeapp.util.extensions

import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import com.example.smarthomeapp.base.scene.BaseApplication.Companion.getInstance as appInstance

object ResourceXs {

    fun getResources(): Resources {
        return appInstance().resources
    }

    fun getString(@StringRes stringResId: Int): String {
        return appInstance().getString(stringResId)
    }

    fun getString(@StringRes stringResId: Int, vararg formatArgs: Any?): String {
        return appInstance().getString(stringResId, formatArgs)
    }

    fun getQuantityString(@PluralsRes stringResId: Int, quantity: Int): String {
        return getResources().getQuantityString(stringResId, quantity)
    }

    fun getQuantityString(
        @PluralsRes stringResId: Int, quantity: Int,
        vararg formatArgs: Any?
    ): String {
        return getResources().getQuantityString(stringResId, quantity, *formatArgs)
    }

    @ColorInt
    fun getColor(@ColorRes colorResId: Int): Int {
        return ResourcesCompat.getColor(getResources(), colorResId, null)
    }

    fun getColorAsHexString(@ColorRes colorResId: Int): String {
        val color = getColor(colorResId)
        return String.format("#%06X", 0xFFFFFF and color)
    }

    fun getDimensionPixelSize(@DimenRes dimesResId: Int): Int {
        return getResources().getDimensionPixelSize(dimesResId)
    }

    fun getStringArray(@ArrayRes arrayResId: Int): Array<String?> {
        return getResources().getStringArray(arrayResId)
    }

    fun getDrawable(@DrawableRes drawableResId: Int): Drawable? {
        return ResourcesCompat.getDrawable(
            getResources(),
            drawableResId,
            appInstance().theme
        )
    }

    fun setBackgroundTint(view: View, @ColorRes color: Int) {
        ViewCompat.setBackgroundTintList(
            view,
            ContextCompat.getColorStateList(view.context, color)
        )
    }

    fun getDimensionPixelSize(
        typedArray: TypedArray,
        @StyleableRes attrResId: Int,
        @DimenRes defaultDimenResId: Int
    ): Int {
        return typedArray.getDimensionPixelSize(
            attrResId,
            getResources().getDimensionPixelSize(defaultDimenResId)
        )
    }
}