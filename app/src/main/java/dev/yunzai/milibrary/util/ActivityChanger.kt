package dev.yunzai.milibrary.util

import android.content.Context
import android.content.Intent
import dev.yunzai.milibrary.activities.BookDetailActivity
import dev.yunzai.milibrary.activities.BookmarkDetailActivity
import dev.yunzai.milibrary.activities.ReviewEditActivity
import dev.yunzai.milibrary.activities.ReviewListActivity
import dev.yunzai.milibrary.constant.EXTRA_BOOK_ID
import dev.yunzai.milibrary.constant.EXTRA_BOOK_MARK_ID
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

fun Context.goToReviewListActivity(bookId: Int) {
    Intent(this, ReviewListActivity::class.java).run {
        putExtra(EXTRA_BOOK_ID, bookId)
        startActivity(this)
    }
}

fun Context.goToBookmarkDetailActivity(bookId: Int, bookmarkId: Int) {
    Intent(this, BookmarkDetailActivity::class.java).run {
        putExtra(EXTRA_BOOK_ID, bookId)
        putExtra(EXTRA_BOOK_MARK_ID, bookmarkId)
        startActivity(this)
    }
}
