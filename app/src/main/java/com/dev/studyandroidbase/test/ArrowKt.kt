package com.dev.studyandroidbase.test

import com.dev.studyandroidbase.extension.Either

fun main() {
}

class NonEmptyList<out A>(val head: A, val tail: List<A>): AbstractList<A>() {
	override val size: Int
		get() = tail.size

	override fun get(index: Int): A {
		return tail[index]
	}

}

sealed class EitherTest<out A, out B> {
	data class Left<out A>(val a: A): EitherTest<A, Nothing>() // Error data va thuong bi bo qua
	data class Right<out B>(val b: B): EitherTest<Nothing, B>() // get success data co the dung flatMap , map, filter,...

	fun fold(fnL: (A) -> Any, fnR: (B) -> Any): Any =
		when(this) {
			is Left -> fnL(a)
			is Right -> fnR(b)
		}
}
