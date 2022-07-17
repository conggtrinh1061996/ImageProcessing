package com.dev.studyandroidbase.di

import com.dev.studyandroidbase.data.network.ApiClient
import com.dev.studyandroidbase.repository.MarsRerepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
	
	@Singleton
	@Provides
	fun provideMarsRepository(apiClient: ApiClient): MarsRerepositoryImpl {
		return MarsRerepositoryImpl(apiClient)
	}
}