package dev.yunzai.milibrary.api

import dev.yunzai.milibrary.data.model.JwtResponse
import dev.yunzai.milibrary.data.model.RefreshRequest
import dev.yunzai.milibrary.data.model.SignInResponse
import dev.yunzai.milibrary.data.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface NoAuthApi {
    @POST("/api/user/refresh")
    fun refreshToken(@Body refreshRequest: RefreshRequest): Single<JwtResponse>

    @POST("/api/user/signup")
    fun signUp(@Body user: User): Completable

    @POST("/api/user/signin")
    fun signIn(@Body user: User): Single<SignInResponse>

    @POST("/api/user/forgot-password")
    fun findPassword(@Body user: User): Completable

    @POST("/api/user/signup/resend-email")
    fun resendEmail(@Body user: User): Completable
}
