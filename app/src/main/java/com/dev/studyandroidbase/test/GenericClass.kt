package com.dev.studyandroidbase.test

fun main() {
	val object1 = GenericClass(10)
	val object2 = GenericClass("This is Generic Classh")
	println(object1)
	println(object2)
}

class GenericClass<out T>(input: T) {
	init {
		println("Generic Class type: $input")
	}
}