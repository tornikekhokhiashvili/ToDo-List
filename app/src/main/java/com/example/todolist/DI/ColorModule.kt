package com.example.todolist.DI

import android.content.Context
import com.example.todolist.utils.ColorManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ColorModule {
    @Provides
    @Singleton
    fun provideColorManager(@ApplicationContext context: Context): ColorManager {
        return ColorManager(context)
    }
}