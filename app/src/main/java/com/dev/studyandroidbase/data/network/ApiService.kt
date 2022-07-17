package com.dev.studyandroidbase.data.network

import com.dev.studyandroidbase.data.model.Mars
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
	@GET
	suspend fun fetchMars(@Url url: String): ApiResponse<List<Mars>>
}