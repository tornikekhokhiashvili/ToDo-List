package com.example.todolist.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.todolist.Domain.dataBase.DataBaseManagement
import com.example.todolist.Domain.model.Task
import com.example.todolist.Domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseManager @Inject constructor(
    private val dataBaseHelper:DatabaseHelper
): DataBaseManagement {
    private val dataBase: SQLiteDatabase by lazy { dataBaseHelper.writableDatabase }
    override fun getTodos(): Flow<List<Todo>> = flow {
        val todos = mutableListOf<Todo>()
        val cursor = dataBase.rawQuery("SELECT * FROM ${DatabaseHelper.TODO_TABLE_NAME}",null)
        cursor.use {
            while (it.moveToNext()){
                val id=cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.TODO_ID))
                val name=cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TODO_NAME))
                todos.add(Todo(id,name))
            }
        }
        emit(todos)
    }
    override fun addTodo(name: String): Long {
        val value= ContentValues().apply {
            put(DatabaseHelper.TODO_NAME,name)
        }
        return insertItem(DatabaseHelper.TODO_TABLE_NAME,value)
    }

    override fun updateTodo(todoId: Long?, newName: String): Int {
        val value=ContentValues().apply {
            put(DatabaseHelper.TODO_NAME,newName)
        }
        val whereClause="${DatabaseHelper.TODO_ID}=?"
        val whereArgs=arrayOf(todoId.toString())
        return updateItem(DatabaseHelper.TODO_TABLE_NAME,value,whereClause,whereArgs)
    }
    override fun deleteTodo(todo: Todo): Int {
        val listArgs= arrayOf(todo.id.toString())
        val deleteTodo=dataBase.delete(DatabaseHelper.TODO_TABLE_NAME,DatabaseHelper.TODO_ID+"=?",listArgs)
        val deleteTask=dataBase.delete(DatabaseHelper.TASK_TABLE_NAME,DatabaseHelper.TODO_ID+"=?",listArgs)
        return deleteTodo+deleteTask
    }
    override fun getTasks(todoId: Long): Flow<List<Task>> = flow{
        val tasks= mutableListOf<Task>()
        val selection ="${DatabaseHelper.TODO_ID}=?"
        val selectArgs=arrayOf(todoId.toString())
        val cursor=dataBase.query(
            DatabaseHelper.TASK_TABLE_NAME,
            null,
            selection,
            selectArgs,
            null,
            null,
            null
        )
        cursor.use {
            while (cursor.moveToNext()){
                val id=cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.TASK_ID))
                val name=cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TASK_NAME))
                val isCompleted=cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.TASK_IS_COMPLETED))
                tasks.add(Task(id,name,isCompleted,todoId))
            }

    }
        emit(tasks)
    }
    override fun addTask(task: Task): Long {
        val value=ContentValues().apply {
            put(DatabaseHelper.TASK_NAME,task.name)
            put(DatabaseHelper.TASK_IS_COMPLETED,task.isCompleted)
            put(DatabaseHelper.TODO_ID,task.todoId)
        }
        return insertItem(DatabaseHelper.TASK_TABLE_NAME,value)
    }

    override fun updateTask(task: Task): Int {
        val value = ContentValues().apply {
            put(DatabaseHelper.TASK_NAME, task.name)
            put(DatabaseHelper.TASK_IS_COMPLETED, task.isCompleted)
        }
        val whereClause = "${DatabaseHelper.TASK_ID} = ? AND ${DatabaseHelper.TASK_ID} =?"
        val whereArgs = arrayOf(task.id.toString(), task.todoId.toString())
        return updateItem(DatabaseHelper.TASK_TABLE_NAME, value, whereClause, whereArgs)
    }
    override fun deleteTask(taskId: Long): Int {
        val whereClause="${DatabaseHelper.TASK_ID} = ?"
        val whereArgs=arrayOf(taskId.toString())
        return deleteItem(DatabaseHelper.TASK_TABLE_NAME,whereClause,whereArgs)
    }

     private fun insertItem(tableName: String, value: ContentValues): Long {
        return dataBase.insert(tableName,null,value)
    }
     private fun updateItem(tableName: String,
                            value: ContentValues,
                            whereClause: String,
                            whereArgs: Array<String>): Int {
        return dataBase.update(tableName,value,whereClause,whereArgs)
    }
     private fun deleteItem(tableName: String,
                            whereClause: String,
                            whereArgs: Array<String>): Int {
        return dataBase.delete(tableName,whereClause,whereArgs)
    }
}

