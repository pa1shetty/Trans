package com.example.trans.utillity.di

import android.content.Context
import androidx.room.Room
import com.example.trans.data.database.AppDatabase
import com.example.trans.data.database.DataBaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "MyDb"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDataBaseRepository(appDatabase: AppDatabase): DataBaseRepository {
        return DataBaseRepository(appDatabase)
    }
}