package dev.yunzai.milibrary.api

import dev.yunzai.milibrary.data.model.Book
import dev.yunzai.milibrary.data.model.BookList
import dev.yunzai.milibrary.data.model.Review
import dev.yunzai.milibrary.data.model.ReviewList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Url
import retrofit2.http.PATCH

interface AuthApi {
    @GET("/api/book/{bookId}")
    fun getBookDetail(@Path("bookId") bookId: Int): Single<Book>

    @GET("/api/book/random")
    fun getRandomBook(@Query("size") size: Int): Single<BookList>

    @GET("/api/books")
    fun getBookList(
        @Query("limit") limit: Int,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("sortBy") sortBy: String
    ): Single<BookList>

    @GET("/api/books")
    fun getBookList(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("sort") sort: String
    ): Single<BookList>

    @GET("/api/search/book")
    fun searchBook(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("target") target: String
    ): Single<BookList>

    @POST("/api/book/{bookId}/review")
    fun postReview(@Path("bookId") bookId: Int, @Body review: Review): Single<Review>

    @DELETE("/api/book/{bookId}/review/{reviewId}")
    fun deleteReview(@Path("bookId") bookId: Int, @Path("reviewId") reviewId: Int): Completable

    @PATCH("/api/book/{bookId}/review/{reviewId}")
    fun patchReview(@Path("bookId") bookId: Int, @Path("reviewId") reviewId: Int, @Body review: Review): Single<Review>

    @GET("/api/book/{bookId}/review/my")
    fun getMyReview(@Path("bookId") bookId: Int): Single<Review>

    @GET("/api/book/{bookId}/reviews")
    fun getSpecificBookReview(
        @Path("bookId") bookId: Int,
        @Query("limit") limit: Int,
        @Query("order") order: String,
        @Query("sortBy") sortBy: String
    ): Single<ReviewList>

    @GET
    fun getSpecificBookReview(@Url url: String): Single<ReviewList>

    @GET("/api/review/random")
    fun getRandomReview(@Query("size") size: Int): Single<ReviewList>

    @GET
    fun getRandomReview(@Url url: String): Single<ReviewList>

    @GET("/api/reviews/my")
    fun getMyTotalReview(
        @Query("limit") limit: Int,
        @Query("order") order: String,
        @Query("sortBy") sortBy: String
    ): Single<ReviewList>

    @GET
    fun getMyTotalReview(@Url url: String): Single<ReviewList>
}
