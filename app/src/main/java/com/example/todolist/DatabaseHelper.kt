package com.example.todolist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "ToDoList.db"
        private const val DATABASE_VERSION = 1
         const val TABLE_LISTS = "lists"
         const val TABLE_ITEMS = "items"
         const val COLUMN_LIST_ID = "list_id"
         const val COLUMN_LIST_NAME = "list_name"
         const val COLUMN_ITEM_ID = "item_id"
         const val COLUMN_ITEM_LIST_ID = "list_id"
         const val COLUMN_ITEM_NAME = "item_name"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // Create 'lists' table
        val createListsTable = """
            CREATE TABLE $TABLE_LISTS (
                $COLUMN_LIST_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_LIST_NAME TEXT
            )
        """.trimIndent()

        // Create 'items' table
        val createItemsTable = """
            CREATE TABLE $TABLE_ITEMS (
                $COLUMN_ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ITEM_LIST_ID INTEGER,
                $COLUMN_ITEM_NAME TEXT,
                FOREIGN KEY ($COLUMN_ITEM_LIST_ID) REFERENCES $TABLE_LISTS($COLUMN_LIST_ID)
            )
        """.trimIndent()
        db?.execSQL(createListsTable)
        db?.execSQL(createItemsTable)
    }

    fun insertItem(itemName: String): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ITEM_NAME, itemName)
        val itemId = db.insert(TABLE_ITEMS, null, values)
        db.close()
        return itemId
    }
    @SuppressLint("Range")
    fun getAllItems(): List<String> {
        val db = readableDatabase
        val items = mutableListOf<String>()

        val cursor = db.query(
            TABLE_ITEMS,
            arrayOf(COLUMN_ITEM_NAME),
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val itemName = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME))
            items.add(itemName)
        }

        cursor.close()
        db.close()

        return items
    }
    fun deleteItem(itemName: String) {
        val db = this.writableDatabase
        db.delete(TABLE_ITEMS, "$COLUMN_ITEM_NAME=?", arrayOf(itemName))
        db.close()
    }
    fun updateItem(itemId: Long,newName: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ITEM_NAME, newName)
        db.update(TABLE_ITEMS, values, "$COLUMN_ITEM_ID = ?", arrayOf(itemId.toString()))
        db.close()
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}