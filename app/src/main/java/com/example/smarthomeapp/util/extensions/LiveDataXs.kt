@file:Suppress("unused")

package com.example.smarthomeapp.util.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun LiveData<Boolean>.unbox(): Boolean = this.value ?: false

fun LiveData<Long>.unbox(): Long = this.value ?: 0L

fun LiveData<Int>.unbox(): Int = this.value ?: 0

fun LiveData<Float>.unbox(): Float = this.value ?: 0.0f

fun LiveData<Double>.unbox(): Double = this.value ?: 0.0

fun LiveData<Short>.unbox(): Short = this.value ?: 0

fun MutableLiveData<Boolean>.toggle() {
    this.value = this.value?.let { !it } ?: true
}

operator fun MutableLiveData<Long>.plus(arg: Long) {
    this.value = this.value?.let { it + arg } ?: arg
}

operator fun MutableLiveData<Long>.minus(arg: Long) {
    this.value = this.value?.let { it - arg } ?: -arg
}

operator fun MutableLiveData<Long>.times(arg: Long) {
    this.value = this.value?.let { it * arg } ?: 0L
}

operator fun MutableLiveData<Long>.div(arg: Long) {
    this.value = this.value?.let { it / arg } ?: 0L
}

operator fun MutableLiveData<Int>.plus(arg: Int) {
    this.value = this.value?.let { it + arg } ?: arg
}

operator fun MutableLiveData<Int>.minus(arg: Int) {
    this.value = this.value?.let { it - arg } ?: -arg
}

operator fun MutableLiveData<Int>.times(arg: Int) {
    this.value = this.value?.let { it * arg } ?: 0
}

operator fun MutableLiveData<Int>.div(arg: Int) {
    this.value = this.value?.let { it / arg } ?: 0
}

operator fun MutableLiveData<Double>.plus(arg: Double) {
    this.value = this.value?.let { it + arg } ?: arg
}

operator fun MutableLiveData<Double>.minus(arg: Double) {
    this.value = this.value?.let { it - arg } ?: -arg
}

operator fun MutableLiveData<Double>.times(arg: Double) {
    this.value = this.value?.let { it * arg } ?: 0.0
}

operator fun MutableLiveData<Double>.div(arg: Double) {
    this.value = this.value?.let { it / arg } ?: 0.0
}

operator fun MutableLiveData<Float>.plus(arg: Float) {
    this.value = this.value?.let { it + arg } ?: arg
}

operator fun MutableLiveData<Float>.minus(arg: Float) {
    this.value = this.value?.let { it - arg } ?: -arg
}

operator fun MutableLiveData<Float>.times(arg: Float) {
    this.value = this.value?.let { it * arg } ?: 0.0f
}

operator fun MutableLiveData<Float>.div(arg: Float) {
    this.value = this.value?.let { it / arg } ?: 0.0f
}

operator fun <T> LiveData<List<T>>.contains(arg: T) = value?.let { arg in it } ?: false

operator fun <T> LiveData<List<T>>.get(arg: Int) = value?.get(arg)

operator fun <T> LiveData<MutableList<T>>.set(index: Int, arg: T) = value?.set(index, arg)

fun <T> mediator(combine: () -> T, vararg sources: LiveData<*>): MediatorLiveData<T> {
    return MediatorLiveData<T>().apply {
        Observer<Any> { _ -> this.setValue(combine.invoke()) }.also {
            sources.forEach { source ->
                this.addSource(source, it)
            }
        }
    }
}