package dev.yunzai.milibrary.data.remote

import dev.yunzai.milibrary.api.AuthApi
import dev.yunzai.milibrary.data.BookMarkDataSource
import dev.yunzai.milibrary.data.model.Bookmark
import dev.yunzai.milibrary.data.model.BookmarkList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class BookmarkRemoteDataSource(
    private val authApi: AuthApi
) : BookMarkDataSource {
    override fun createBookMark(bookId: Int): Completable {
        return authApi.createBookMark(bookId)
    }

    override fun getBookmark(bookId: Int): Single<Bookmark> {
        return authApi.getBookmark(bookId)
    }

    override fun deleteMyBookMark(bookmarkId: Int): Completable {
        return authApi.deleteMyBookMark(bookmarkId)
    }

    override fun editMyBookMark(bookmarkId: Int, bookmark: Bookmark): Completable {
        return authApi.editMyBookMark(bookmarkId, bookmark)
    }

    override fun getMyAllBookMark(): Single<BookmarkList> {
        return authApi.getMyAllBookMark()
    }
}
