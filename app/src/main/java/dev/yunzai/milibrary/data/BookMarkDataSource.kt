package dev.yunzai.milibrary.data

import dev.yunzai.milibrary.data.model.Bookmark
import dev.yunzai.milibrary.data.model.BookmarkList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface BookMarkDataSource {
    fun createBookMark(bookId: Int): Completable

    fun getBookmark(bookId: Int): Single<Bookmark>

    fun deleteMyBookMark(bookmarkId: Int): Completable

    fun editMyBookMark(bookmarkId: Int, bookmark: Bookmark): Completable

    fun getMyAllBookMark(): Single<BookmarkList>
}
