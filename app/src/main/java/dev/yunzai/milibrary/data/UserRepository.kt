package dev.yunzai.milibrary.data

import io.reactivex.rxjava3.core.Single

class UserRepository : UserDataSource {
    override fun refreshToken(): Single<Unit> {
        TODO("Not yet implemented")
    }
}
