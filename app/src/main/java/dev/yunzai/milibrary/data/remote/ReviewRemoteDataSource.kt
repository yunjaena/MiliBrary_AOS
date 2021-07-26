package dev.yunzai.milibrary.data.remote

import dev.yunzai.milibrary.api.AuthApi
import dev.yunzai.milibrary.data.ReviewDataSource
import dev.yunzai.milibrary.data.model.Review
import dev.yunzai.milibrary.data.model.ReviewList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ReviewRemoteDataSource(
    private val authApi: AuthApi
) : ReviewDataSource {
    override fun postReview(bookId: Int, review: Review): Single<Review> {
        return authApi.postReview(bookId, review)
    }

    override fun deleteReview(bookId: Int, reviewId: Int): Completable {
        return authApi.deleteReview(bookId, reviewId)
    }

    override fun patchReview(bookId: Int, reviewId: Int, review: Review): Single<Review> {
        return authApi.patchReview(bookId, reviewId, review)
    }

    override fun getMyReview(bookId: Int): Single<Review> {
        return authApi.getMyReview(bookId)
    }

    override fun getSpecificBookReview(bookId: Int, limit: Int, order: String, sortBy: String): Single<ReviewList> {
        return authApi.getSpecificBookReview(bookId, limit, order, sortBy)
    }

    override fun getSpecificBookReview(url: String): Single<ReviewList> {
        return authApi.getSpecificBookReview(url)
    }

    override fun getRandomReview(size: Int): Single<ReviewList> {
        return authApi.getRandomReview(size)
    }

    override fun getRandomReview(url: String): Single<ReviewList> {
        return authApi.getRandomReview(url)
    }

    override fun getMyTotalReview(limit: Int, order: String, sortBy: String): Single<ReviewList> {
        return authApi.getMyTotalReview(limit, order, sortBy)
    }

    override fun getMyTotalReview(url: String): Single<ReviewList> {
        return authApi.getMyTotalReview(url)
    }
}
