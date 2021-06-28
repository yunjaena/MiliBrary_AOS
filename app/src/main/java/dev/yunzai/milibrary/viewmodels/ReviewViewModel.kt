package dev.yunzai.milibrary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import dev.yunzai.milibrary.data.ReviewRepository
import dev.yunzai.milibrary.data.model.Review
import dev.yunzai.milibrary.util.SingleLiveEvent
import dev.yunzai.milibrary.util.handleHttpException
import dev.yunzai.milibrary.util.handleProgress
import dev.yunzai.milibrary.util.withThread
import io.reactivex.rxjava3.kotlin.addTo

class ReviewViewModel(
    private val reviewRepository: ReviewRepository
) : ViewModelBase() {
    val myReview: LiveData<Review>
        get() = _myReview
    private val _myReview = MutableLiveData<Review>()
    val editReviewCompleteEvent = SingleLiveEvent<Boolean>()

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

    fun postReview(bookId: Int, comment: String, score: Double) {
        reviewRepository.postReview(bookId, Review(comment = comment, score = score))
            .handleHttpException()
            .withThread()
            .handleProgress(this)
            .subscribe(
                {
                    editReviewCompleteEvent.call()
                },
                {
                }
            ).addTo(compositeDisposable)
    }

    fun editReview(bookId: Int, reviewId: Int, comment: String, score: Double) {
        reviewRepository.patchReview(bookId, reviewId, Review(comment = comment, score = score))
            .handleHttpException()
            .withThread()
            .handleProgress(this)
            .subscribe(
                {
                    editReviewCompleteEvent.call()
                },
                {
                }
            ).addTo(compositeDisposable)
    }
}
