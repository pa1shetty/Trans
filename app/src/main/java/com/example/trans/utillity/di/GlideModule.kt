package com.example.trans.utillity.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlideModule {
    @Provides
    @Singleton
    fun provideGlideInstance(@ApplicationContext context: Context): RequestManager {
        return Glide.with(context)
    }
}