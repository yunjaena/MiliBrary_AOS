package dev.yunzai.milibrary.data

import dev.yunzai.milibrary.data.model.JwtResponse
import io.reactivex.rxjava3.core.Single

interface UserDataSource {
    fun refreshToken(accessToken: String): Single<JwtResponse>
}
