package dev.yunzai.milibrary.util

import android.content.Context
import android.content.Intent
import dev.yunzai.milibrary.activities.BookDetailActivity
import dev.yunzai.milibrary.activities.ReviewEditActivity
import dev.yunzai.milibrary.constant.EXTRA_BOOK_ID
import dev.yunzai.milibrary.constant.EXTRA_EDIT_MODE

fun Context.goToBookDetailActivity(bookId: Int) {
    Intent(this, BookDetailActivity::class.java).run {
        putExtra(EXTRA_BOOK_ID, bookId)
        startActivity(this)
    }
}

fun Context.goToReviewEditActivity(bookId: Int, isEditMode: Boolean) {
    Intent(this, ReviewEditActivity::class.java).run {
        putExtra(EXTRA_EDIT_MODE, isEditMode)
        putExtra(EXTRA_BOOK_ID, bookId)
        startActivity(this)
    }
}
