package com.dev.studyandroidbase.generic

import android.util.Log
import com.dev.studyandroidbase.utils.AppLogger.TAG
import com.google.android.material.tabs.TabLayout

// define generic class: dinh nghia lop kieu chung
class MutableStack<E> (vararg items: E) {
	private val elements = items.toMutableList()

	fun push(element: E) = elements.add(element)

	fun peek(): E = elements.last()

	fun pop(): E = elements.removeAt(elements.size - 1)

	fun isEmty() = elements.isEmpty()

	fun size() = elements.size

	// Define generic function
	fun <E> mutableStackOf(vararg elements: E) = MutableStack(*elements)

	fun main() {
		val stack = mutableStackOf(1, 0.4, 3.14, 11.2)
		Log.d(TAG, "stack: $stack")
	}
}