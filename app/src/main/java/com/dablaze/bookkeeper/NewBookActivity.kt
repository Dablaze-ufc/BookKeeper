package com.dablaze.bookkeeper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_new_book.*

class NewBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)

        textDate.isVisible = false

        buttonCancel.setOnClickListener {
            finish()
        }

        buttonSave.setOnClickListener {
            val resultIntent = Intent()
            val author = etAuthor.editText?.text.toString().trim()
            val bookName = etBookName.editText?.text.toString().trim()
            val bookDescription = etBookDescription.editText?.text.toString().trim()
            if (TextUtils.isEmpty(author) || TextUtils.isEmpty(bookName)){
                setResult(Activity.RESULT_CANCELED, resultIntent)
            }else{
                resultIntent.putExtra(AUTHOR_NAME,author)
                resultIntent.putExtra(BOOK_NAME,bookName)
                resultIntent.putExtra(BOOK_DESCRIPTION, bookDescription)
                setResult(Activity.RESULT_OK,resultIntent)
                finish()
            }
        }
    }

    companion object{
        const val AUTHOR_NAME = "author_name"
        const val BOOK_NAME ="book_name"
        const val BOOK_DESCRIPTION ="book_description"
    }


}
