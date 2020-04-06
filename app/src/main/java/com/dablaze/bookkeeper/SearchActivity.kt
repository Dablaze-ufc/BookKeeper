package com.dablaze.bookkeeper

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dablaze.bookkeeper.database.Book
import com.dablaze.bookkeeper.viewModel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*

class SearchActivity : AppCompatActivity(), BooksListAdapter.OnDeleteClickListener {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var bookListAdapter :BooksListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel(application)::class.java)

         bookListAdapter = BooksListAdapter(this, this)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerBooks.adapter = bookListAdapter
        recyclerBooks.layoutManager = linearLayoutManager

       searchIntent(intent)

        fab.isVisible = false
    }

    private fun searchIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action){
            val searchQuery = intent.getStringExtra(SearchManager.QUERY)
            searchViewModel.getSearchedBooks("%$searchQuery%").observe(this, Observer{books ->
                books?.let {
                    bookListAdapter.setBooks(books)
                }

            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val bundle = data.extras
                val id = bundle?.getString(EditActivity.ID)!!
                val authorName = bundle.getString(EditActivity.UPDATED_AUTHOR)!!
                val bookName = bundle.getString(EditActivity.UPDATED_BOOK_NAME)!!
                val book = Book(id, authorName, bookName)

                searchViewModel.update(book)

                Toast.makeText(applicationContext, "Updated", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Not saved", Toast.LENGTH_LONG).show()
            }

        }
    }

    companion object {
        const val UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2
    }
    override fun onDeleteClickListener(book: Book) {
        searchViewModel.delete(book)
        Toast.makeText(applicationContext,getString(R.string.delete),Toast.LENGTH_LONG).show()

    }
}
