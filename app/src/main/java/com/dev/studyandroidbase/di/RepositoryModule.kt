package com.dev.studyandroidbase.di

import com.dev.studyandroidbase.data.network.ApiClient
import com.dev.studyandroidbase.repository.MarsRerepository
import com.dev.studyandroidbase.repository.MarsRerepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
	
	@Provides
	@Singleton
	fun provideMarsRepository(apiClient: ApiClient): MarsRerepository {
		return MarsRerepositoryImpl(apiClient)
	}
}