package com.dev.studyandroidbase.test

import kotlin.reflect.KProperty

fun main() {
	val student = Student()
	student.firstName = "Today"
	student.lastName = "Yesterday"
	println("Student: ${student}")
}

val square: (Int, Int) -> Int = { a, b -> a + b}

class Student {
	var firstName: String? by NameDelegate()
	var lastName: String? by NameDelegate()

	override fun toString(): String {
		return "$firstName || $lastName"
	}
}

//
class NameDelegate {
	var formattedValue: String? = null

	operator fun setValue(
		thisRef: Any?,
		property: KProperty<*>,
		value: String?
	) {
		if (value!!.length > 5) {
			formattedValue = value.trim().uppercase()
		}
	}

	operator fun getValue(
		thisRef: Any?,
		property: KProperty<*>
	): String? {
		return formattedValue
	}
}