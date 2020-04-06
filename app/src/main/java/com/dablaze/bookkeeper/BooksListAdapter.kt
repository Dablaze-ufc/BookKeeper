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
       var book = booksList[position]
        holder.setData(book.author,book.bookName,position)
        holder.setListeners()
    }

    fun setBooks(books: List<Book>) {
        booksList = books
        notifyDataSetChanged()
    }

    inner class BooksListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private  var pos :Int = 0

       fun setData(author: String, bookName: String, position: Int) {
           itemView.textAuthor.text = author
           itemView.textBook.text = bookName
           this.pos = position
       }

        fun setListeners() {
            itemView.deleteButton.setOnClickListener {
                onDeleteClickListener.onDeleteClickListener(booksList[pos])

            }

            itemView.editButton.setOnClickListener{
                val intent = Intent(context, EditActivity::class.java)
                intent.putExtra("id",booksList[pos].id)
                intent.putExtra("author_name",booksList[pos].author)
                intent.putExtra("book_name",booksList[pos].bookName)
                (context as Activity).startActivityForResult(intent,MainActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE)
            }

        }

    }
}
