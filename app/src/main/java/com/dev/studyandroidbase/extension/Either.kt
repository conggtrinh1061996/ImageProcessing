package com.dev.studyandroidbase.extension

sealed class Either<out L, out R> {
	
	data class Failure <out L>(val a: L): Either<L, Nothing>()
	
	data class Success<out R>(val b: R): Either<Nothing, R>()
	
	fun <L> left(a: L) = Failure(a)
	
	fun <R> right(b: R) = Success(b)
	
	fun fold(fnL: (L) -> Any, fnR: (R) -> Any): Any =
		when (this) {
			is Failure -> fnL(a)
			is Success -> fnR(b)
		}
}