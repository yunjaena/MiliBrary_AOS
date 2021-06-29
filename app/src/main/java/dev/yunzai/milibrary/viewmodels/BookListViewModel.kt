package dev.yunzai.milibrary.viewmodels

import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import dev.yunzai.milibrary.data.BookRepository
import dev.yunzai.milibrary.data.model.Book
import dev.yunzai.milibrary.util.SingleLiveEvent
import dev.yunzai.milibrary.util.handleHttpException
import dev.yunzai.milibrary.util.handleProgress
import dev.yunzai.milibrary.util.withThread

class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModelBase() {
    val bookListFetchEvent = SingleLiveEvent<ArrayList<Book>?>()
    private var searchTarget: String = TITLE
    private var currentPage = 1
    private var query = ""
    private var totalPage = 1

    fun searchBook(searchTarget: String, query: String) {
        this.currentPage = 1
        this.searchTarget = searchTarget
        this.query = query

        bookRepository.searchBook(BOOK_LIST_SIZE, this.currentPage, this.query, this.searchTarget)
            .handleProgress(this)
            .handleHttpException()
            .withThread()
            .withThread()
            .subscribe(
                {
                    totalPage = it.totalPage ?: 1
                    bookListFetchEvent.value = it.books
                },
                {}
            )
    }

    fun getNextSearchBook() {
        this.currentPage++
        bookRepository.searchBook(BOOK_LIST_SIZE, this.currentPage, this.query, this.searchTarget)
            .handleProgress(this)
            .handleHttpException()
            .withThread()
            .withThread()
            .subscribe(
                {
                    totalPage = it.totalPage ?: 1
                    bookListFetchEvent.value = it.books
                },
                {}
            )
    }

    fun isEndOfList(): Boolean {
        return totalPage == currentPage
    }

    companion object {
        const val BOOK_LIST_SIZE = 10
        const val TITLE = "title"
        const val AUTHOR = "author"
        const val ORDER = "asc"
        const val SORT_BY = "date"
    }
}
