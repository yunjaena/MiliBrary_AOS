package dev.yunzai.milibrary.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import dev.yunzai.milibrary.data.BookRepository
import dev.yunzai.milibrary.data.BookmarkRepository
import dev.yunzai.milibrary.data.model.Bookmark
import dev.yunzai.milibrary.util.SingleLiveEvent
import dev.yunzai.milibrary.util.handleHttpException
import dev.yunzai.milibrary.util.handleProgress
import dev.yunzai.milibrary.util.withThread
import io.reactivex.rxjava3.core.Single

class BookmarkViewModel(
    private val bookmarkRepository: BookmarkRepository,
    private val bookRepository: BookRepository
) : ViewModelBase() {
    val bookmarkFetchEvent = SingleLiveEvent<Bookmark?>()
    val bookmarkUpdateCompleteEvent = SingleLiveEvent<Boolean>()
    val bookmarkDeleteCompleteEvent = SingleLiveEvent<Int>()
    val errorMessageEvent = SingleLiveEvent<@StringRes Int>()
    val bookmark: LiveData<Bookmark?>
        get() = _bookMark
    private val _bookMark = MutableLiveData<Bookmark?>()

    fun getMyBookmark(bookId: Int) {
        getBookmarkStatus(bookId)
            .handleHttpException()
            .handleProgress(this)
            .withThread()
            .subscribe(
                {
                    _bookMark.value = it
                },
                {
                    _bookMark.value = null
                }
            )
    }

    fun getMyAllBookmark() {
        bookmarkRepository.getMyAllBookMark()
            .handleHttpException()
            .withThread()
            .subscribe(
                {
                    it.bookmarks.forEach {
                        bookmarkFetchEvent.value = it
                    }
                },
                {
                    bookmarkFetchEvent.value = null
                }
            )
    }

    fun createBookmark(bookId: Int) {
        bookmarkRepository.createBookMark(bookId)
            .toSingle { true }
            .flatMap { getBookmarkStatus(bookId) }
            .handleHttpException()
            .handleProgress(this)
            .withThread()
            .subscribe(
                {
                    _bookMark.value = it
                },
                {
                    _bookMark.value = null
                }
            )
    }

    fun deleteBookmark(bookmarkId: Int?) {
        if (bookmarkId == null) return
        bookmarkRepository.deleteMyBookMark(bookmarkId)
            .handleHttpException()
            .handleProgress(this)
            .withThread()
            .subscribe(
                {
                    _bookMark.value = null
                    bookmarkDeleteCompleteEvent.value = bookmarkId
                },
                {
                    _bookMark.value = null
                }
            )
    }

    fun updateBookmark(bookmarkId: Int, content: String) {
        if (content.isNullOrEmpty()) {
            errorMessageEvent.value = R.string.write_bookmark_content
        }

        bookmarkRepository.editMyBookMark(bookmarkId, Bookmark(content = content))
            .handleHttpException()
            .handleProgress(this)
            .withThread()
            .subscribe(
                {
                    bookmarkUpdateCompleteEvent.call()
                },
                {
                }
            )
    }

    private fun getBookmarkStatus(bookId: Int): Single<Bookmark> {
        return bookmarkRepository.getMyAllBookMark()
            .map { it.bookmarks.find { bookmark -> bookmark.bookId == bookId } }
    }
}
