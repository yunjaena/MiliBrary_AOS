package dev.yunzai.milibrary.data

import dev.yunzai.milibrary.data.model.JwtResponse
import io.reactivex.rxjava3.core.Single

class UserRepository : UserDataSource {
    override fun refreshToken(accessToken: String): Single<JwtResponse> {
        TODO("Not yet implemented")
    }
}
