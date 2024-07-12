package com.swamyiphyo.booklibrary

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.swamyiphyo.booklibrary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var db : DBHelper
    private var bookId : ArrayList<String> = ArrayList()
    private var bookTitle : ArrayList<String> = ArrayList()
    private var bookAuthor : ArrayList<String> = ArrayList()
    private var bookPages : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view : View = mainBinding.root
        setContentView(view)

        db = DBHelper(this)

        mainBinding.addBtn.setOnClickListener(){
            startActivity(Intent(this, AddActivity::class.java))
        }

        storeDataInArrays()

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mainBinding.mainRV.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = CustomAdapter(this@MainActivity, bookId, bookTitle, bookAuthor, bookPages)
        }

    }
    private fun storeDataInArrays(){
        val cursor = db.getAllBooks()

        if(cursor.count == 0){
            //cursor is empty that means there is no data in our database table
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show()
        }
        else{
            //we have data now we're gonna get data by moving cursor to the last and add to the respective arraylist
            while (cursor.moveToNext()){
                bookId.add(cursor.getString(0))
                bookTitle.add(cursor.getString(1))
                bookAuthor.add(cursor.getString(2))
                bookPages.add(cursor.getString(3))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == 1){
            recreate()
        }
    }
}