package dev.yunzai.milibrary.data

import dev.yunzai.milibrary.data.model.Book
import dev.yunzai.milibrary.data.model.BookList
import io.reactivex.rxjava3.core.Single

interface BookDataSource {
    fun getBookDetail(bookId: Int): Single<Book>

    fun getRandomBook(size: Int): Single<BookList>

    fun getBookList(limit: Int, order: String, page: Int, sortBy: String): Single<BookList>

    fun getBookList(limit: Int, page: Int, sort: String): Single<BookList>

    fun searchBook(limit: Int, page: Int, query: String, target: String): Single<BookList>
}
