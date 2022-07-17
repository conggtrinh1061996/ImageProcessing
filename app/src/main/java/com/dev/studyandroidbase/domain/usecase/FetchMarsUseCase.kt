package com.dev.studyandroidbase.domain.usecase

import com.dev.studyandroidbase.data.model.Mars
import com.dev.studyandroidbase.repository.MarsRerepository
import kotlinx.coroutines.*
import javax.inject.Inject

class FetchMarsUseCase @Inject constructor(
	private val repository: MarsRerepository
) {
	suspend operator fun invoke(
		onResult: (unit: Any?) -> Unit
	) {
		coroutineScope {
			withContext(Dispatchers.IO) {
				val result: MutableList<Mars> = mutableListOf()
					val deferred = async {
						try {
							val responseData = repository.fetchMars()
							responseData.forEach {
								result.add(it)
							}
							result
						} catch (e: Exception) {
							e.printStackTrace()
							result.isEmpty()
						}
					}
				onResult(deferred.await())
			}
		}
	}
	
}