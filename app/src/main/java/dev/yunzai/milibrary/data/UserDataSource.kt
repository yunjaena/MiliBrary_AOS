package dev.yunzai.milibrary.data

import io.reactivex.rxjava3.core.Single

interface UserDataSource {
    // TODO => Change return type
    fun refreshToken(): Single<Unit>
}
