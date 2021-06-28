package dev.yunzai.milibrary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import dev.yunzai.milibrary.data.BookRepository
import dev.yunzai.milibrary.data.ReviewRepository
import dev.yunzai.milibrary.data.model.Book
import dev.yunzai.milibrary.data.model.Review
import dev.yunzai.milibrary.util.handleHttpException
import dev.yunzai.milibrary.util.handleProgress
import dev.yunzai.milibrary.util.withThread
import io.reactivex.rxjava3.kotlin.addTo

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    private val reviewRepository: ReviewRepository
) : ViewModelBase() {
    val bookDetailData: LiveData<Book>
        get() = _bookDetailData
    private val _bookDetailData = MutableLiveData<Book>()
    val myReview: LiveData<Review>
        get() = _myReview
    private val _myReview = MutableLiveData<Review>()

    fun loadBookDetail(bookId: Int) {
        bookRepository.getBookDetail(bookId)
            .handleHttpException()
            .withThread()
            .handleProgress(this)
            .subscribe(
                {
                    _bookDetailData.value = it
                },
                {
                }
            ).addTo(compositeDisposable)
    }

    fun getMyReview(bookId: Int) {
        reviewRepository.getMyReview(bookId)
            .handleHttpException()
            .withThread()
            .handleProgress(this)
            .subscribe(
                {
                    _myReview.value = it
                },
                {
                }
            ).addTo(compositeDisposable)
    }
}
