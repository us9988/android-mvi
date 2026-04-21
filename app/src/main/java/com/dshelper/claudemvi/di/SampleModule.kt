package com.dshelper.claudemvi.di

import com.dshelper.claudemvi.data.repository.SampleRepositoryImpl
import com.dshelper.claudemvi.domain.repository.SampleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SampleModule {

    @Binds
    @Singleton
    abstract fun bindSampleRepository(impl: SampleRepositoryImpl): SampleRepository
}
