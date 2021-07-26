package dev.yunzai.milibrary.data

import dev.yunzai.milibrary.data.model.Book
import dev.yunzai.milibrary.data.model.BookList
import io.reactivex.rxjava3.core.Single

class BookRepository(
    private val bookRemoteDataSource: BookDataSource
) : BookDataSource {
    override fun getBookDetail(bookId: Int): Single<Book> {
        return bookRemoteDataSource.getBookDetail(bookId)
    }

    override fun getRandomBook(size: Int): Single<BookList> {
        return bookRemoteDataSource.getRandomBook(size)
    }

    override fun getBookList(limit: Int, order: String, page: Int, sortBy: String): Single<BookList> {
        return bookRemoteDataSource.getBookList(limit, order, page, sortBy)
    }

    override fun getBookList(limit: Int, page: Int, sort: String): Single<BookList> {
        return bookRemoteDataSource.getBookList(limit, page, sort)
    }

    override fun searchBook(limit: Int, page: Int, query: String, target: String): Single<BookList> {
        return bookRemoteDataSource.searchBook(limit, page, query, target)
    }
}
