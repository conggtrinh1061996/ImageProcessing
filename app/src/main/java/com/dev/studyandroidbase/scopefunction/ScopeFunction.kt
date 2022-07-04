package com.dev.studyandroidbase.scopefunction

fun main() {
	// let()
	val empty = "test".let {
		customPrint(it)
		it.length
	}
	println("is Empty: $empty")
	printNonNull("my string")
	// run()
	getNullableLength(null)
	getNullableLength("")
	getNullableLength("String android kotlin")
	// apply(): thường dùng khởi tạo đối tượng
	val jake = Person()
	val stringDescription = jake.apply {
		name = "Jake"
		age = 25
		about = "Android Developer"
	}.toString()
	println("Apply: $stringDescription")
	// also(): thêm vào cacsc hoạt động bổ sung cuả object
	jake.also {
		writeCreationLog(it)
	}
}

fun customPrint(string: String) {
	println(string.uppercase())
}

// let()
fun printNonNull(string: String?) {
	println("Printing \"$string\": ")
	string?.let {
		print("\t")
		customPrint(it)
		println()
	}
}

// run()
fun getNullableLength(ns: String?) {
	println("for \"$ns\"")
	ns?.run {
		println("\tis empty: " + isEmpty())
		println("\tlength = $length")
		length
	}
}

// apply() and also()
data class Person(var name: String, var age: Int, var about: String) {
	constructor() : this("", 0, "")
}

fun writeCreationLog(p: Person) {
	println("Also: A new person ${p.name} was created")
}