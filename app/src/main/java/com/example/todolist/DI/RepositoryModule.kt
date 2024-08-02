package com.example.todolist.DI

import com.example.todolist.Domain.dataBase.DataBaseManagement
import com.example.todolist.Domain.repository.TaskRepository
import com.example.todolist.Domain.repository.TodoRepository
import com.example.todolist.data.DataBaseManager
import com.example.todolist.data.repository.TaskRepositoryImpl
import com.example.todolist.data.repository.TodoRepositoryImpl
import com.example.todolist.utils.ColorManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun provideTaskRepository(taskRepositoryImpl: TaskRepositoryImpl): TaskRepository

    @Binds
    @Singleton
    fun provideTodoRepository(todoRepositoryImpl: TodoRepositoryImpl): TodoRepository

    @Binds
    @Singleton
    fun provideDataBaseManager(dataBaseManagerImpl: DataBaseManager): DataBaseManagement
}