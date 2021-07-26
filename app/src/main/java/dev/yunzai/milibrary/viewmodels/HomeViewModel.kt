package dev.yunzai.milibrary.viewmodels

import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import dev.yunzai.milibrary.constant.SORT_TYPE_BOOK_YEAR_DESC_QRT_DESC
import dev.yunzai.milibrary.data.BookRepository
import dev.yunzai.milibrary.data.ReviewRepository
import dev.yunzai.milibrary.data.model.BookList
import dev.yunzai.milibrary.data.model.Review
import dev.yunzai.milibrary.util.SingleLiveEvent
import dev.yunzai.milibrary.util.handleHttpException
import dev.yunzai.milibrary.util.withThread
import io.reactivex.rxjava3.kotlin.addTo

class HomeViewModel(
    private val bookRepository: BookRepository,
    private val reviewRepository: ReviewRepository
) : ViewModelBase() {
    val newBookList = SingleLiveEvent<BookList>()
    val randomBookList = SingleLiveEvent<BookList>()
    val randomReview = SingleLiveEvent<Review>()

    fun getNewBookList() {
        bookRepository.getBookList(NEW_BOOK_LIST_LIMIT, 1, NEW_BOOK_LIST_SORT_BY)
            .handleHttpException()
            .withThread()
            .subscribe({
                newBookList.value = it
            }, {

            }).addTo(compositeDisposable)
    }

    fun getRandomBookList() {
        bookRepository.getRandomBook(RANDOM_LIST_SIZE)
            .handleHttpException()
            .withThread()
            .subscribe({
                randomBookList.value = it
            }, {

            }).addTo(compositeDisposable)
    }

    fun getRandomReview() {
        reviewRepository.getRandomReview(RANDOM_LIST_SIZE)
            .toObservable()
            .flatMapIterable { it.reviews }
            .flatMapSingle { review ->
                bookRepository.getBookDetail(review.bookId!!)
                    .map { book ->
                        Review(
                            book = book,
                            bookId = review.bookId,
                            comment = review.comment,
                            createdAt = review.createdAt,
                            id = review.id,
                            narasarangId = review.narasarangId,
                            score = review.score
                        )
                    }
            }
            .handleHttpException()
            .withThread()
            .subscribe({
                randomReview.value = it
            }, {

            }).addTo(compositeDisposable)
    }

    companion object {
        const val RANDOM_LIST_SIZE = 10
        const val NEW_BOOK_LIST_LIMIT = 10
        const val NEW_BOOK_LIST_SORT_BY = SORT_TYPE_BOOK_YEAR_DESC_QRT_DESC
    }

}
