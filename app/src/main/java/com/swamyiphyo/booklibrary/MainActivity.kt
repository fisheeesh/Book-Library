package com.swamyiphyo.booklibrary

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == 1){
            recreate()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_all){
            confirmDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun storeDataInArrays(){
        val cursor = db.getAllBooks()

        if(cursor.count == 0){
            //cursor is empty that means there is no data in our database table
            mainBinding.textViewNoData.visibility = View.VISIBLE
            mainBinding.imageViewEmpty.visibility = View.VISIBLE
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

    private fun confirmDialog(){
        val alert = AlertDialog.Builder(this)
        alert.apply {
            setTitle("Delete All Books?")
            setMessage("Are you sure you want to delete all the books?")
            setPositiveButton("Yes"){_,_ ->
                db.deleteAllBooks()
                recreate()
            }
            setNegativeButton("No"){_,_->}
        }
        alert.create().show()
    }
}