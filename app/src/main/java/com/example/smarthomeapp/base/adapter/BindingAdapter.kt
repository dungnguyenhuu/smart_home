package com.example.smarthomeapp.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.smarthomeapp.base.holder.BindingHolder

abstract class BindingAdapter<M> :
    BaseAdapter<BindingHolder<out ViewDataBinding, M>>() {
    var mItemClickListener: OnItemClick<M>? = null

    override fun onBindViewHolder(
        holder: BindingHolder<out ViewDataBinding, M>,
        position: Int
    ) = holder.onBind(position, getItem(position))

    override fun onViewDetachedFromWindow(holder: BindingHolder<out ViewDataBinding, M>) {
        super.onViewDetachedFromWindow(holder)
        holder.onUnbind()
    }

    protected fun setHolderRootViewAsItemClick(target: BindingHolder<out ViewDataBinding, M>) {
        target.registerRootViewAsHolderClickEvent(mItemClickListener)
    }

    fun setItemClickListener(itemClick: OnItemClick<M>?) {
        mItemClickListener = itemClick
    }

    override fun clear() {}
    abstract fun getItem(position: Int): M

    companion object {
        protected fun <T : ViewDataBinding> inflate(
            parent: ViewGroup,
            @LayoutRes layoutId: Int
        ): T {
            return LayoutInflater.from(parent.context).let {
                DataBindingUtil.inflate(it, layoutId, parent, false)
            }
        }
    }
}