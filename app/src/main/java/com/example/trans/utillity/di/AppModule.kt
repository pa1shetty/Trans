package com.example.trans.utillity.di

import android.content.Context
import com.example.trans.data.datastore.AppDataStore
import com.example.trans.utillity.AesLibrary
import com.example.trans.utillity.UtilClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun commonFunctionsWithContext(@ApplicationContext appContext: Context) =
        UtilClass()

    @Provides
    @Singleton
    fun provideAppDataStore(
        @ApplicationContext app: Context,
        provideAesLibrary: AesLibrary
    ) = AppDataStore(app, provideAesLibrary)

}