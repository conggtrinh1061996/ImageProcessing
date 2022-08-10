package com.dev.studyandroidbase.test

import android.view.View
import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
	
}

fun TextView.text(): ReadWriteProperty<Any, String> =
	object : ReadWriteProperty<Any, String> {
		override fun getValue(thisRef: Any, property: KProperty<*>): String {
			return text.toString()
		}

		override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
			text = value
		}
	}

fun View.isVisible(keepBounds: Boolean): ReadWriteProperty<Any, Boolean> =
	object : ReadWriteProperty<Any, Boolean> {
		override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
			return visibility == View.VISIBLE
		}

		override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
			visibility = when {
				value -> View.VISIBLE
				keepBounds -> View.INVISIBLE
				else -> View.GONE
			}
		}

	}

var View.visible: Boolean
	get() = visibility == View.VISIBLE
	set(value) {
		if (value) {
			if (visibility != View.VISIBLE) visibility = View.VISIBLE
		} else {
			if (visibility == View.VISIBLE) visibility = View.GONE
		}
	}