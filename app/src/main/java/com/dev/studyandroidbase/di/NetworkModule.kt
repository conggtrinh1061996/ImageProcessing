package com.dev.studyandroidbase.di

import com.dev.studyandroidbase.BuildConfig
import com.dev.studyandroidbase.data.network.ApiClient
import com.dev.studyandroidbase.data.network.ApiService
import com.dev.studyandroidbase.utils.Constants.BASE_URL
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
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
	
	@Provides
	@Singleton
	fun provideInterceptor(): HttpLoggingInterceptor {
		return HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}
	}
	
	@Provides
	@Singleton
	fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
		val builder = OkHttpClient.Builder().readTimeout(30, SECONDS)
		if (BuildConfig.DEBUG) {
			builder.addInterceptor(interceptor)
		}
		return builder.build()
	}
	
	@Provides
	@Singleton
	fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
		return Retrofit.Builder()
			.client(okHttpClient)
			.baseUrl(BASE_URL)
			.addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
			.addConverterFactory(MoshiConverterFactory.create().asLenient())
			.build()
	}
	
	@Provides
	@Singleton
	fun provideApiService(retrofit: Retrofit): ApiService {
		return retrofit.create(ApiService::class.java)
	}
	
	@Provides
	@Singleton
	fun provideApiClient(apiService: ApiService): ApiClient = ApiClient(apiService)
}