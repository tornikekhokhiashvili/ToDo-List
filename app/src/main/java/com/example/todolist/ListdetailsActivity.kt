package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class ListdetailsActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listdetails)
        listView=findViewById(R.id.list_view_2)
        setContentView(R.layout.activity_listdetails)
        dbHelper = DatabaseHelper(this)
        val items = dbHelper.getAllItems()
        val adapter= ArrayAdapter(this,R.layout.list_for_items,R.id.text_view_2,items)
        listView.adapter=adapter
    }
}