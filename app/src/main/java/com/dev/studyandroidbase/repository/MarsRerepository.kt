package com.dev.studyandroidbase.repository

import com.dev.studyandroidbase.data.model.Mars
import com.dev.studyandroidbase.data.network.ApiClient
import com.dev.studyandroidbase.utils.Constants.BASE_URL_FIRST
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

interface MarsRerepository {
	suspend fun fetchMars(): List<Mars>
}

class MarsRerepositoryImpl @Inject constructor(
	private val apiClient: ApiClient
): MarsRerepository {
	
	override suspend fun fetchMars(): List<Mars> {
		val response = apiClient.fetchMars(BASE_URL_FIRST)
		var listMars = emptyList<Mars>()
		response.suspendOnSuccess {
			listMars = data
		}
		return listMars
	}
}