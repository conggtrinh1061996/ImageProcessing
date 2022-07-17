package com.dev.studyandroidbase.data.network

import javax.inject.Inject

class ApiClient @Inject constructor(
	private val apiService: ApiService
) {
	suspend fun fetchMars(url: String) = apiService.fetchMars(url)
}