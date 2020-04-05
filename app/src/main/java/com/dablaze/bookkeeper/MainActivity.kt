package com.dablaze.bookkeeper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dablaze.bookkeeper.database.Book
import com.dablaze.bookkeeper.viewModel.BookViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var bookViewModel:BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        bookViewModel = ViewModelProviders.of(this).get(BookViewModel(application)::class.java)

        val bookAdapter = BooksListAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerBooks.adapter = bookAdapter
        recyclerBooks.layoutManager = linearLayoutManager

        bookViewModel.allBooks.observe(this, Observer{books ->
            books?.let {
                bookAdapter.setBooks(books)
            }

        })
        fab.setOnClickListener {
          val intent = Intent(this, NewBookActivity::class.java)
            startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if (data != null){
                val bundle = data.extras
                val id = UUID.randomUUID().toString()
                val authorName = bundle?.getString(NewBookActivity.AUTHOR_NAME)!!
                val bookName = bundle.getString(NewBookActivity.BOOK_NAME)!!
                val book = Book(id,authorName,bookName)

                bookViewModel.insert(book)

                Toast.makeText(applicationContext,R.string.saved,Toast.LENGTH_LONG).show()
            } else{
                Toast.makeText(applicationContext,"Empty data",Toast.LENGTH_LONG).show()
            }

        }else if(requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if (data != null){
                val bundle = data.extras
                val id = bundle?.getString(EditActivity.ID)!!
                val authorName = bundle.getString(EditActivity.UPDATED_AUTHOR)!!
                val bookName = bundle.getString(EditActivity.UPDATED_BOOK_NAME)!!
                val book = Book(id,authorName,bookName)

                bookViewModel.update(book)

                Toast.makeText(applicationContext,"Updated",Toast.LENGTH_LONG).show()
            } else{
                Toast.makeText(applicationContext,"Not saved",Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(applicationContext,R.string.not_saved,Toast.LENGTH_LONG).show()
        }


    }

    companion object{
        const val NEW_NOTE_ACTIVITY_REQUEST_CODE = 1
        const val UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2
    }

}
