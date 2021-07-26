package dev.yunzai.milibrary.data

import dev.yunzai.milibrary.data.model.JwtResponse
import dev.yunzai.milibrary.data.model.SignInResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UserDataSource {
    fun refreshToken(): Completable

    fun refreshTokenFromServer(refreshToken: String): Single<JwtResponse>

    fun signUp(id: String, password: String): Completable

    fun saveToken(accessToken: String, refreshToken: String): Completable

    fun getRefreshToken(): Single<String>

    fun getAccessToken(): Single<String>

    fun signInServer(id: String, password: String): Single<SignInResponse>

    fun signIn(id: String, password: String): Completable

    fun saveUserInfo(id: String, nickName: String): Completable

    fun findPassword(id: String): Completable

    fun resendEmail(id: String): Completable

    fun logout(): Completable
}
