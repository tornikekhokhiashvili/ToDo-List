package com.example.todolist.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelper @Inject constructor(
   @ApplicationContext context: Context?
): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "ToDoList.db"
        private const val DATABASE_VERSION = 1
        const val TODO_TABLE_NAME = "todo_table"
        const val TASK_TABLE_NAME = "task_table"

        const val TODO_ID = "todo_id"
        const val TODO_NAME = "todo_name"

        const val TASK_ID = "task_id"
        const val TASK_NAME = "task_name"
        const val TASK_IS_COMPLETED = "is_completed"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
            CREATE TABLE $TODO_TABLE_NAME (
                $TODO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $TODO_NAME TEXT
            )
            """
        )

        db?.execSQL(
            """
            CREATE TABLE $TASK_TABLE_NAME (
                $TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $TASK_NAME TEXT,
                $TASK_IS_COMPLETED INTEGER,
                $TODO_ID INTEGER,
                FOREIGN KEY($TODO_ID) REFERENCES $TODO_TABLE_NAME($TODO_ID)
            )
            """
        )
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TODO_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TASK_TABLE_NAME")
        onCreate(db)
    }
}