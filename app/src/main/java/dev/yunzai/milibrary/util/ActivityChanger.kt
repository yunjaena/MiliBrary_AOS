package dev.yunzai.milibrary.util

import android.content.Context
import android.content.Intent
import dev.yunzai.milibrary.activities.BookDetailActivity
import dev.yunzai.milibrary.constant.EXTRA_BOOK_ID

fun Context.goToBookDetailActivity(bookId: Int) {
    Intent(this, BookDetailActivity::class.java).run {
        putExtra(EXTRA_BOOK_ID, bookId)
        startActivity(this)
    }
}
