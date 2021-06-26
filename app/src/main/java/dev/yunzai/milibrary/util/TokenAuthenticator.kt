package dev.yunzai.milibrary.util

import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.Logger
import dev.yunzai.milibrary.constant.REFRESH_TOKEN
import dev.yunzai.milibrary.data.UserRepository
import dev.yunzai.milibrary.data.model.JwtResponse
import io.reactivex.rxjava3.subjects.PublishSubject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val userRepository: UserRepository
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        try {
            if (response.code() != 401) return null
            val tokenResult = getRefreshedJwtTokenResult()

            if (tokenResult.isFailure) {
                Logger.d("$TAG token updated failed : ${tokenResult.exceptionOrNull()}")
                return null
            }

            val jwtToken = tokenResult.getOrNull()
            if (jwtToken?.accessToken?.token == null) {
                Logger.d("$TAG token is empty")
                return null
            }
            Logger.d("$TAG token updated : $jwtToken")
            return getRequest(response, jwtToken.accessToken.token)
        } catch (e: Exception) {
            return null
        }
    }

    private fun getRefreshedJwtTokenResult(): Result<JwtResponse> {
        val publishSubject: PublishSubject<Result<JwtResponse>> = PublishSubject.create()
        val refreshToken = Hawk.get(REFRESH_TOKEN, "")
        userRepository.refreshToken(refreshToken)
            .subscribe({
                publishSubject.onNext(Result.success(it))
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
