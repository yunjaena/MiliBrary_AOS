package dev.yunzai.milibrary.data.remote

import dev.yunzai.milibrary.api.AuthApi
import dev.yunzai.milibrary.api.NoAuthApi
import dev.yunzai.milibrary.data.UserDataSource
import dev.yunzai.milibrary.data.model.JwtResponse
import dev.yunzai.milibrary.data.model.RefreshRequest
import dev.yunzai.milibrary.data.model.SignInResponse
import dev.yunzai.milibrary.data.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class UserRemoteDataSource(
    private val noAuthApi: NoAuthApi,
    private val authApi: AuthApi
) : UserDataSource {
    override fun refreshToken(): Completable {
        return Completable.never()
    }

    override fun saveToken(accessToken: String, refreshToken: String): Completable {
        return Completable.never()
    }

    override fun refreshTokenFromServer(refreshToken: String): Single<JwtResponse> {
        return noAuthApi.refreshToken(RefreshRequest(refreshToken))
    }

    override fun signUp(id: String, password: String): Completable {
        return noAuthApi.signUp(User(id, password))
    }

    override fun getRefreshToken(): Single<String> {
        return Single.never()
    }

    override fun getAccessToken(): Single<String> {
        return Single.never()
    }

    override fun signInServer(id: String, password: String): Single<SignInResponse> {
        return noAuthApi.signIn(User(id, password))
    }

    override fun signIn(id: String, password: String): Completable {
        return Completable.never()
    }

    override fun saveUserInfo(id: String, nickName: String): Completable {
        return Completable.never()
    }

    override fun findPassword(id: String): Completable {
        return noAuthApi.findPassword(User(id))
    }

    override fun resendEmail(id: String): Completable {
        return noAuthApi.resendEmail(User(id))
    }

    override fun logout(): Completable {
        return authApi.signout()
    }
}
