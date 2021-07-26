package dev.yunzai.milibrary.data

import dev.yunzai.milibrary.data.model.JwtResponse
import dev.yunzai.milibrary.data.model.SignInResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class UserRepository(
    private val userLocalDataSource: UserDataSource,
    private val userRemoteDataSource: UserDataSource
) : UserDataSource {
    override fun refreshToken(): Completable {
        return getRefreshToken().flatMap { token ->
            refreshTokenFromServer(token)
        }.flatMapCompletable {
            saveToken(it.accessToken?.token ?: "", it.refreshToken?.token ?: "")
        }
    }

    override fun refreshTokenFromServer(refreshToken: String): Single<JwtResponse> {
        return userRemoteDataSource.refreshTokenFromServer(refreshToken)
    }

    override fun saveToken(accessToken: String, refreshToken: String): Completable {
        return userLocalDataSource.saveToken(accessToken, refreshToken)
    }

    override fun signUp(id: String, password: String): Completable {
        return userRemoteDataSource.signUp(id, password)
    }

    override fun getRefreshToken(): Single<String> {
        return userLocalDataSource.getRefreshToken()
    }

    override fun getAccessToken(): Single<String> {
        return userLocalDataSource.getAccessToken()
    }

    override fun signInServer(id: String, password: String): Single<SignInResponse> {
        return userRemoteDataSource.signInServer(id, password)
    }

    override fun signIn(id: String, password: String): Completable {
        return signInServer(id, password)
            .flatMapCompletable {
                saveToken(
                    it.tokens.accessToken?.token ?: "",
                    it.tokens.refreshToken?.token ?: ""
                ).andThen(saveUserInfo(it.narasarangId, it.nickname))
            }
    }

    override fun saveUserInfo(id: String, nickName: String): Completable {
        return userLocalDataSource.saveUserInfo(id, nickName)
    }

    override fun findPassword(id: String): Completable {
        return userRemoteDataSource.findPassword(id)
    }

    override fun resendEmail(id: String): Completable {
        return userRemoteDataSource.resendEmail(id)
    }

    override fun logout(): Completable {
        return userRemoteDataSource.logout()
            .andThen(userLocalDataSource.logout())
    }
}
