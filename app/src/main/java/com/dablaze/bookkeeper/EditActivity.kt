package com.dablaze.bookkeeper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_book.*

class EditActivity : AppCompatActivity() {

    var id:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)

        val bundle:Bundle? = intent.extras

        bundle?.let {
            id = bundle.getString("id")
            val author = bundle.getString("author_name")
            val book = bundle.getString("book_name")

            etAuthor.editText?.setText( author)
            etBookName.editText?.setText(book)
        }



        buttonSave.setOnClickListener {
            val updatedAuthor = etAuthor.editText?.text.toString().trim()
            val updatedBookName = etBookName.editText?.text.toString().trim()

            val updateIntent = Intent()

            updateIntent.putExtra(ID,id)
            updateIntent.putExtra(UPDATED_AUTHOR, updatedAuthor)
            updateIntent.putExtra(UPDATED_BOOK_NAME,updatedBookName)
            setResult(Activity.RESULT_OK,updateIntent)
        finish()
        }

        buttonCancel.setOnClickListener {
        finish()
        }
    }
    companion object{
        const val ID ="id"
        const val UPDATED_AUTHOR = "author_name"
        const val UPDATED_BOOK_NAME ="book_name"
    }
}
