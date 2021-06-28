package dev.yunzai.milibrary.data

import dev.yunzai.milibrary.data.model.Review
import dev.yunzai.milibrary.data.model.ReviewList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ReviewDataSource {
    fun postReview(bookId: Int, review: Review): Single<Review>

    fun deleteReview(bookId: Int, reviewId: Int): Completable

    fun patchReview(bookId: Int, reviewId: Int, review: Review): Single<Review>

    fun getMyReview(bookId: Int): Single<Review>

    fun getSpecificBookReview(bookId: Int, limit: Int, order: String, sortBy: String): Single<ReviewList>

    fun getSpecificBookReview(url: String): Single<ReviewList>

    fun getRandomReview(size: Int): Single<ReviewList>

    fun getRandomReview(url: String): Single<ReviewList>

    fun getMyTotalReview(limit: Int, order: String, sortBy: String): Single<ReviewList>

    fun getMyTotalReview(url: String): Single<ReviewList>
}
