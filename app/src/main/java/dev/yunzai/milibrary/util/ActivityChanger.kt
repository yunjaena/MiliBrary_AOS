package dev.yunzai.milibrary.util

import android.content.Context
import android.content.Intent
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dev.yunzai.milibrary.activities.BookDetailActivity
import dev.yunzai.milibrary.activities.BookListActivity
import dev.yunzai.milibrary.activities.BookmarkDetailActivity
import dev.yunzai.milibrary.activities.LoginActivity
import dev.yunzai.milibrary.activities.MyReviewListActivity
import dev.yunzai.milibrary.activities.ReviewEditActivity
import dev.yunzai.milibrary.activities.ReviewListActivity
import dev.yunzai.milibrary.constant.EXTRA_BOOK_ID
import dev.yunzai.milibrary.constant.EXTRA_BOOK_LIST_SORT_TYPE
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

fun Context.goToBookListActivity(sortType: String) {
    Intent(this, BookListActivity::class.java).run {
        putExtra(EXTRA_BOOK_LIST_SORT_TYPE, sortType)
        startActivity(this)
    }
}

fun Context.goToLoginActivity(isRestart: Boolean = true) {
    val intent = Intent(this, LoginActivity::class.java).run {
        if (isRestart) {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(this)
    }
}

fun Context.goToOssLibraryActivity() {
    Intent(this, OssLicensesMenuActivity::class.java).run {
        startActivity(this)
    }
}

fun Context.goToMyReviewActivity() {
    Intent(this, MyReviewListActivity::class.java).run {
        startActivity(this)
    }
}
