package dev.yunzai.milibrary.data

import dev.yunzai.milibrary.data.model.Review
import dev.yunzai.milibrary.data.model.ReviewList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ReviewRepository(
    private val reviewRemoteDataSource: ReviewDataSource
) : ReviewDataSource {
    override fun postReview(bookId: Int, review: Review): Single<Review> {
        return reviewRemoteDataSource.postReview(bookId, review)
    }

    override fun deleteReview(bookId: Int, reviewId: Int): Completable {
        return reviewRemoteDataSource.deleteReview(bookId, reviewId)
    }

    override fun patchReview(bookId: Int, reviewId: Int, review: Review): Single<Review> {
        return reviewRemoteDataSource.patchReview(bookId, reviewId, review)
    }

    override fun getMyReview(bookId: Int): Single<Review> {
        return reviewRemoteDataSource.getMyReview(bookId)
    }

    override fun getSpecificBookReview(bookId: Int, limit: Int, order: String, sortBy: String): Single<ReviewList> {
        return reviewRemoteDataSource.getSpecificBookReview(bookId, limit, order, sortBy)
    }

    override fun getSpecificBookReview(url: String): Single<ReviewList> {
        return reviewRemoteDataSource.getSpecificBookReview(url)
    }

    override fun getRandomReview(size: Int): Single<ReviewList> {
        return reviewRemoteDataSource.getRandomReview(size)
    }

    override fun getRandomReview(url: String): Single<ReviewList> {
        return reviewRemoteDataSource.getRandomReview(url)
    }

    override fun getMyTotalReview(limit: Int, order: String, sortBy: String): Single<ReviewList> {
        return reviewRemoteDataSource.getMyTotalReview(limit, order, sortBy)
    }

    override fun getMyTotalReview(url: String): Single<ReviewList> {
        return reviewRemoteDataSource.getMyTotalReview(url)
    }
}
