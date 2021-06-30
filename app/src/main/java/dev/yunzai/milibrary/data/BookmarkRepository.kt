package dev.yunzai.milibrary.data

import dev.yunzai.milibrary.data.model.Bookmark
import dev.yunzai.milibrary.data.model.BookmarkList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class BookmarkRepository(
    private val bookmarkRemoteDataSource: BookMarkDataSource
) : BookMarkDataSource {
    override fun createBookMark(bookId: Int): Completable {
        return bookmarkRemoteDataSource.createBookMark(bookId)
    }

    override fun getBookmark(bookId: Int): Single<Bookmark> {
        return bookmarkRemoteDataSource.getBookmark(bookId)
    }

    override fun deleteMyBookMark(bookmarkId: Int): Completable {
        return bookmarkRemoteDataSource.deleteMyBookMark(bookmarkId)
    }

    override fun editMyBookMark(bookmarkId: Int, bookmark: Bookmark): Completable {
        return bookmarkRemoteDataSource.editMyBookMark(bookmarkId, bookmark)
    }

    override fun getMyAllBookMark(): Single<BookmarkList> {
        return bookmarkRemoteDataSource.getMyAllBookMark()
    }
}
