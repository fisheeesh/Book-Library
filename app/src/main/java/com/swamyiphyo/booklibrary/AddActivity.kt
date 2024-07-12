package com.swamyiphyo.booklibrary

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.swamyiphyo.booklibrary.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    private lateinit var addBinding: ActivityAddBinding
    private lateinit var db : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addBinding = ActivityAddBinding.inflate(layoutInflater)
        val view : View = addBinding.root
        setContentView(view)

        db = DBHelper(this)

        addBinding.addBookBtn.setOnClickListener(){
            val titleInput = addBinding.editTextBookTitle.text.toString().trim()
            val authorInput = addBinding.editTextBookAuthor.text.toString().trim()
            val pagesInput = addBinding.editTextBookPages.text.toString().trim()

            if(titleInput.isEmpty() || authorInput.isEmpty() || pagesInput.isEmpty()){
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            else{
                if(db.addBook(titleInput, authorInput, pagesInput.toInt()) == -1L){
                    Toast.makeText(this, "Failed to add book", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show()
                    addBinding.editTextBookTitle.text.clear()
                    addBinding.editTextBookAuthor.text.clear()
                    addBinding.editTextBookPages.text.clear()
                }
            }
        }

    }
}