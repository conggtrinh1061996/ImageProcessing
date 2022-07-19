package com.dev.studyandroidbase.base

import com.dev.studyandroidbase.extension.Either
import com.dev.studyandroidbase.extension.Failure
import kotlinx.coroutines.*

abstract class UseCase<in Param, out Result> where Result: Any {
	
	abstract suspend fun run(param: Param): Either<Failure, Result>
	
	operator fun invoke(
		param: Param,
		scope: CoroutineScope = GlobalScope,
		onResult: (Either<Failure, Result>) -> Unit) = scope.launch(Dispatchers.Main) {
			val deferred = async(Dispatchers.IO) {
				run(param)
			}
			onResult(deferred.await())
		}
	
	class None
}