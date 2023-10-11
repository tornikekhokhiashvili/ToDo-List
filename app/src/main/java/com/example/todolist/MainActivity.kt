package com.example.todolist

import com.example.todolist.DatabaseHelper.Companion.COLUMN_LIST_NAME
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.todolist.DatabaseHelper.Companion.TABLE_LISTS
import com.example.todolist.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var addbutton:FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        addbutton=findViewById(R.id.add_button)
        addbutton.setOnClickListener {
            showAddListDialog()
        }
    }
    private fun showAddListDialog() {
        val builder = AlertDialog.Builder(this)

        val input = EditText(this)
        builder.setView(input)
        builder.setTitle("Add ToDo List")
        builder.setPositiveButton("Add") { _, _ ->
            val newListName = input.text.toString().trim()
            if (newListName.isNotEmpty()) {
                val dbHelper = DatabaseHelper(this)
                dbHelper.insertItem(newListName)
                val intent = Intent(this, NewEditListActivity::class.java)
                startActivity(intent)
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

}