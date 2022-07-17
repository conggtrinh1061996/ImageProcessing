package com.dev.studyandroidbase.di

import com.dev.studyandroidbase.BuildConfig
import com.dev.studyandroidbase.data.network.ApiClient
import com.dev.studyandroidbase.data.network.ApiService
import com.dev.studyandroidbase.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	
	@Singleton
	@Provides
	fun provideInterceptor(): HttpLoggingInterceptor {
		return HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}
	}
	
	@Singleton
	@Provides
	fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
		val builder = OkHttpClient.Builder().readTimeout(30, SECONDS)
		if (BuildConfig.DEBUG) {
			builder.addInterceptor(interceptor)
		}
		return builder.build()
	}
	
	@Singleton
	@Provides
	fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
		return Retrofit.Builder()
			.client(okHttpClient)
			.baseUrl(BASE_URL)
			.addConverterFactory(MoshiConverterFactory.create().asLenient())
			.build()
	}

	@Singleton
	@Provides
	fun provideApiService(retrofit: Retrofit): ApiService {
		return retrofit.create(ApiService::class.java)
	}
	
	@Singleton
	@Provides
	fun provideApiClient(apiService: ApiService): ApiClient = ApiClient(apiService)
}