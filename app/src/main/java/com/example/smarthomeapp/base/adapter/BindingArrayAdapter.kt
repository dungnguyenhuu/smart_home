package com.example.smarthomeapp.base.adapter

abstract class BindingArrayAdapter<M> @JvmOverloads constructor(data: MutableList<out M>? = null) :
    BindingAdapter<M?>() {

    private var dataList: MutableList<M> = ArrayList()

    init {
        data?.let {
            dataList.addAll(it)
        }
    }

    override fun getItem(position: Int): M? = dataList[position]

    override fun getItemCount() = dataList.size

    fun addData(data: Collection<M>) {
        val startOffset = itemCount
        if (data.isNotEmpty()) {
            dataList.addAll(data)
            notifyItemRangeInserted(startOffset, data.size)
        }
    }

    fun addData(start: Int, data: Collection<M>) {
        start.coerceAtMost(dataList.size).let { startOffset ->
            if (data.isNotEmpty()) {
                dataList.addAll(start, data)
                notifyItemRangeInserted(startOffset, data.size)
            } else {
                notifyItemRangeInserted(startOffset, 0)
            }
        }
    }

    fun addData(data: M) {
        dataList.add(data)
        notifyItemInserted(itemCount - 1)
    }

    fun addData(position: Int, data: M) {
        dataList.add(position, data)
        notifyItemInserted(position)
    }

    fun update(index: Int, newItem: M) {
        if (index >= 0 && index < dataList.size) {
            dataList.set(index, newItem)
            notifyItemChanged(index)
        }
    }

    fun remove(index: Int) {
        if (index >= 0 && index < dataList.size) {
            dataList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun remove(item: M) {
        dataList.indexOf(item).let { index ->
            if (index >= 0) {
                dataList.removeAt(index)
                notifyItemRemoved(index)
            }
        }
    }

    fun remove(start: Int, end: Int) {
        if (start in 0..end && end < dataList.size) {
            dataList.subList(start, end + 1).clear()
            notifyItemRangeRemoved(start, end - start + 1)
        }
    }

    override fun clear() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun getData(): List<M> = dataList

    fun setData(data: Collection<M>?) {
        dataList.apply {
            clear()
            data?.let {
                this.addAll(it)
                notifyDataSetChanged()
            }
        }
    }
}