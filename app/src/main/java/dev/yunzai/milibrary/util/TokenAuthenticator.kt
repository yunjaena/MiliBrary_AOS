package dev.yunzai.milibrary.util

import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.Logger
import dev.yunzai.milibrary.api.NoAuthApi
import dev.yunzai.milibrary.constant.ACCESS_TOKEN
import dev.yunzai.milibrary.constant.REFRESH_TOKEN
import dev.yunzai.milibrary.data.model.RefreshRequest
import io.reactivex.rxjava3.subjects.PublishSubject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val noAuthApi: NoAuthApi
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        try {
            if (response.code() != 401) return null

            val tokenResult = getRefreshedJwtTokenResult()

            tokenResult.onFailure {
                Logger.d("$TAG token updated failed : ${tokenResult.exceptionOrNull()}")
                return null
            }

            tokenResult.onSuccess { accessToken ->
                if (accessToken.isEmpty()) {
                    Logger.d("$TAG token is empty")
                    return null
                }
                Logger.d("$TAG token updated : $accessToken")
                return getRequest(response, accessToken)
            }
        } catch (e: Exception) {
            return null
        }

        return null
    }

    private fun getRefreshedJwtTokenResult(): Result<String> {
        val publishSubject: PublishSubject<Result<String>> = PublishSubject.create()
        val refreshToken = Hawk.get(REFRESH_TOKEN, "")
        noAuthApi.refreshToken(RefreshRequest(refreshToken))
            .subscribe({
                if (it.accessToken?.token != null) {
                    Hawk.put(ACCESS_TOKEN, it.accessToken.token)
                }

                if (it.refreshToken?.token != null) {
                    Hawk.put(REFRESH_TOKEN, it.refreshToken.token)
                }
                publishSubject.onNext(Result.success(it.accessToken?.token ?: ""))
                publishSubject.onComplete()
            }) {
                publishSubject.onNext(Result.failure(it))
                publishSubject.onComplete()
            }
        return publishSubject.blockingSingle()
    }

    private fun getRequest(response: Response, token: String): Request {
        return response.request()
            .newBuilder()
            .removeHeader("Authorization")
            .addHeader("Authorization", "Bearer $token")
            .build()
    }

    companion object {
        const val TAG = "TokenAuthenticator"
    }
}
