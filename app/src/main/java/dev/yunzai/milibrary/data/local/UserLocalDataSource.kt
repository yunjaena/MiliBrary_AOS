package dev.yunzai.milibrary.data.local

import com.orhanobut.hawk.Hawk
import dev.yunzai.milibrary.constant.ACCESS_TOKEN
import dev.yunzai.milibrary.constant.REFRESH_TOKEN
import dev.yunzai.milibrary.constant.USER_ID
import dev.yunzai.milibrary.constant.USER_NICKNAME
import dev.yunzai.milibrary.data.UserDataSource
import dev.yunzai.milibrary.data.model.JwtResponse
import dev.yunzai.milibrary.data.model.SignInResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class UserLocalDataSource : UserDataSource {
    override fun refreshToken(): Completable {
        return Completable.never()
    }

    override fun saveToken(accessToken: String, refreshToken: String): Completable {
        return Completable.create {
            if (!accessToken.isNullOrEmpty())
                Hawk.put(ACCESS_TOKEN, accessToken)
            if (!refreshToken.isNullOrEmpty())
                Hawk.put(REFRESH_TOKEN, refreshToken)
            it.onComplete()
        }
    }

    override fun refreshTokenFromServer(refreshToken: String): Single<JwtResponse> {
        return Single.never()
    }

    override fun signUp(id: String, password: String): Completable {
        return Completable.never()
    }

    override fun getRefreshToken(): Single<String> {
        return Single.just(Hawk.get(REFRESH_TOKEN, ""))
    }

    override fun getAccessToken(): Single<String> {
        return Single.just(Hawk.get(ACCESS_TOKEN, ""))
    }

    override fun signInServer(id: String, password: String): Single<SignInResponse> {
        return Single.never()
    }

    override fun signIn(id: String, password: String): Completable {
        return Completable.never()
    }

    override fun saveUserInfo(id: String, nickName: String): Completable {
        return Completable.create {
            Hawk.put(USER_ID, id)
            Hawk.put(USER_NICKNAME, nickName)
            it.onComplete()
        }
    }

    override fun findPassword(id: String): Completable {
        return Completable.never()
    }

    override fun resendEmail(id: String): Completable {
        return Completable.never()
    }

    override fun logout(): Completable {
        return Completable.create {
            Hawk.deleteAll()
            it.onComplete()
        }
    }
}
