package com.dev.studyandroidbase.domain.usecase

import com.dev.studyandroidbase.base.UseCase
import com.dev.studyandroidbase.base.UseCase.None
import com.dev.studyandroidbase.data.model.Mars
import com.dev.studyandroidbase.extension.CustomFailure
import com.dev.studyandroidbase.extension.Either
import com.dev.studyandroidbase.extension.Failure
import com.dev.studyandroidbase.repository.MarsRerepository
import kotlinx.coroutines.*
import javax.inject.Inject

class FetchMarsUseCase @Inject constructor(
	private val repository: MarsRerepository
): UseCase<None, List<Mars>>() {
	
	override suspend fun run(param: None): Either<Failure, List<Mars>> {
		return try {
			val listMars = repository.fetchMars()
			Either.Success(listMars)
		} catch (e: Exception) {
			Either.Failure(CustomFailure(e))
		}
	}
	
}