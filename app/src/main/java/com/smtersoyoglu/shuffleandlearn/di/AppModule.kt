package com.smtersoyoglu.shuffleandlearn.di

import android.content.Context
import android.content.SharedPreferences
import com.smtersoyoglu.shuffleandlearn.data.datasource.WordDataSource
import com.smtersoyoglu.shuffleandlearn.data.repository.WordRepository
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
    fun provideWordDataSource (@ApplicationContext context: Context): WordDataSource {
        return WordDataSource(context)
    }

    @Provides
    @Singleton
    fun provideWordRepository(dataSource: WordDataSource): WordRepository {
        return WordRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("learned_words", Context.MODE_PRIVATE)
    }
}