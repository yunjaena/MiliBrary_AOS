package dev.yunzai.milibrary.data.remote

import dev.yunzai.milibrary.api.AuthApi
import dev.yunzai.milibrary.data.BookDataSource
import dev.yunzai.milibrary.data.model.Book
import dev.yunzai.milibrary.data.model.BookList
import io.reactivex.rxjava3.core.Single

class BookRemoteDataSource(
    private val authApi: AuthApi
) : BookDataSource {
    override fun getBookDetail(bookId: Int): Single<Book> {
        return authApi.getBookDetail(bookId)
    }

    override fun getRandomBook(size: Int): Single<BookList> {
        return authApi.getRandomBook(size)
    }

    override fun getBookList(limit: Int, order: String, page: Int, sortBy: String): Single<BookList> {
        return authApi.getBookList(limit, order, page, sortBy)
    }

    override fun getBookList(limit: Int, page: Int, sort: String): Single<BookList> {
        return authApi.getBookList(limit, page, sort)
    }

    override fun searchBook(limit: Int, page: Int, query: String, target: String): Single<BookList> {
        return authApi.searchBook(limit, page, query, target)
    }
}
