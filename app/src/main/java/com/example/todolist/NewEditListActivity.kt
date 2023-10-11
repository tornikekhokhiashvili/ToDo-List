package com.example.todolist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewEditListActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var addbutton: FloatingActionButton
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var listView: ListView
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_edit_list)
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        listView=findViewById(R.id.list_view)
        addbutton=findViewById(R.id.add_button_2)
        dbHelper = DatabaseHelper(this)
    val items = dbHelper.getAllItems()
        val adapter=ArrayAdapter(this,R.layout.list_for_todo,R.id.text_view,items)
        listView.adapter=adapter
        listView.setOnItemClickListener { _, _, position, id ->
            val menuIcon = findViewById<ImageView>(R.id.menu_icon)
            val popupMenu = PopupMenu(this@NewEditListActivity, menuIcon)
            popupMenu.menuInflater.inflate(R.menu.menu_main, popupMenu.menu)
            val itemNameToDelete = adapter.getItem(position)
            val itemIToUpdate=adapter.getItemId(position)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        showEditItemNameDialog(itemIToUpdate)
//                        adapter.notifyDataSetChanged()
                        Log.d("dd", items.toString())
                        true
                    }
                    R.id.action_delete -> {
                        dbHelper.deleteItem(itemNameToDelete!!)
                        adapter.remove(itemNameToDelete)
                        adapter.notifyDataSetChanged()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
        addbutton.setOnClickListener {
            showAddListDialog()
        }

    }
    private fun showEditItemNameDialog(itemToEdit: Long) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Edit Item Name")
        val input = EditText(this)
        alertDialog.setView(input)
        alertDialog.setPositiveButton("Update") { dialog, _ ->
            val newName = input.text.toString().trim()
            if (newName.isNotEmpty()) {
                dbHelper.updateItem(itemToEdit, newName)
                dbHelper.getAllItems()
            }
            dialog.dismiss()
        }
        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
    }
    private fun showAddListDialog() {
        val builder = AlertDialog.Builder(this)

        val input = EditText(this)
        builder.setView(input)
        builder.setTitle("Add ToDo item")
        builder.setPositiveButton("Add") { _, _ ->
            val newListName = input.text.toString().trim()
            if (newListName.isNotEmpty()) {
                val dbHelper = DatabaseHelper(this)
                dbHelper.insertItem(newListName)
                val intent = Intent(this, ListdetailsActivity::class.java)
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