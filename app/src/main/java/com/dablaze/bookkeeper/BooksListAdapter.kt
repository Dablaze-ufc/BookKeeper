package com.dablaze.bookkeeper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dablaze.bookkeeper.database.Book
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class BooksListAdapter(private val context:Context, private val onDeleteClickListener: OnDeleteClickListener) : RecyclerView.Adapter<BooksListAdapter.BooksListViewHolder>()  {
    interface OnDeleteClickListener{
        fun onDeleteClickListener(book:Book)
    }

    private var booksList:List<Book> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksListViewHolder {
       val itemView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        return BooksListViewHolder(itemView)
    }

    override fun getItemCount(): Int = booksList.size

    override fun onBindViewHolder(holder: BooksListViewHolder, position: Int) {
       val book = booksList[position]
        holder.setData(book.lastUpdated,book.bookName,position)
        holder.setListeners()
    }

    fun setBooks(books: List<Book>) {
        booksList = books
        notifyDataSetChanged()
    }

    inner class BooksListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private  var pos :Int = 0

       fun setData(lastUpdated: Date?, bookName: String, position: Int) {
           itemView.textAuthor.text = bookName
           itemView.textBook.text = getFormattedDate(lastUpdated)
           this.pos = position
       }

        private fun getFormattedDate(lastUpdated: Date?): CharSequence? {
            var time = "Last Updated: "
            time += lastUpdated?.let {
                val simpleFormatter = SimpleDateFormat("HH:mm d MMM, yyyy", Locale.getDefault())
                simpleFormatter.format(lastUpdated)
            } ?: "NOT FOUND"
            return time
        }

        fun setListeners() {
            itemView.deleteButton.setOnClickListener {
                onDeleteClickListener.onDeleteClickListener(booksList[pos])

            }

            itemView.setOnClickListener{
                val intent = Intent(context, EditActivity::class.java)
                intent.putExtra("id",booksList[pos].id)
                intent.putExtra("author_name",booksList[pos].author)
                intent.putExtra("book_name",booksList[pos].bookName)
                intent.putExtra("book_desc", booksList[pos].description)
                intent.putExtra("last_updated",getFormattedDate(booksList[pos].lastUpdated))
                (context as Activity).startActivityForResult(intent,MainActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE)
            }

        }

    }
}
