package com.swamyiphyo.booklibrary

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.swamyiphyo.booklibrary.databinding.ActivityMainBinding
import com.swamyiphyo.booklibrary.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var updateBinding: ActivityUpdateBinding
    private lateinit var id : String
    private lateinit var title : String
    private lateinit var author : String
    private lateinit var pages : String
    private lateinit var db : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        val view = updateBinding.root
        setContentView(view)

        db = DBHelper(this)

        getAndSetIntentData()

        supportActionBar?.title = title

        updateBinding.updateBtn.setOnClickListener(){
            val updateTitle = updateBinding.editTextBookTitleUpdate.text.toString().trim()
            val updateAuthor = updateBinding.editTextBookAuthorUpdate.text.toString().trim()
            val updatePages = updateBinding.editTextBookPagesUpdate.text.toString().trim()
            if(updateTitle.isEmpty() || updateAuthor.isEmpty() || updatePages.isEmpty()){
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            else{
                if(db.updateBook(id, updateTitle, updateAuthor, updatePages.toInt()) == -1){
                    Toast.makeText(this, "Failed to update book", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show()
                    startActivityForResult(Intent(this, MainActivity::class.java), 1)
                    finish()
                }
            }
        }
        updateBinding.deleteBtn.setOnClickListener(){

        }
    }
    private fun getAndSetIntentData(){
        if(intent.hasExtra("id") || intent.hasExtra("title") || intent.hasExtra("author") || intent.hasExtra("pages")){
            //getting data from intent
            id = intent.getStringExtra("id").toString()
            title = intent.getStringExtra("title").toString()
            author = intent.getStringExtra("author").toString()
            pages = intent.getStringExtra("pages").toString()

            //setting data to the input fields
            updateBinding.editTextBookTitleUpdate.setText(title)
            updateBinding.editTextBookAuthorUpdate.setText(author)
            updateBinding.editTextBookPagesUpdate.setText(pages)
        }
        else{
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show()
        }
    }
}