package dev.yunzai.milibrary.viewmodels

import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import dev.yunzai.milibrary.data.UserRepository
import dev.yunzai.milibrary.util.SingleLiveEvent
import dev.yunzai.milibrary.util.handleHttpException
import dev.yunzai.milibrary.util.handleProgress
import dev.yunzai.milibrary.util.withThread

class MyPageViewModel(
    private val userRepository: UserRepository
) : ViewModelBase() {
    val logOutEvent = SingleLiveEvent<Boolean>()

    fun logOut() {
        userRepository.logout()
            .handleProgress(this)
            .handleHttpException()
            .withThread()
            .subscribe(
                {
                    logOutEvent.value = true
                },
                {
                    logOutEvent.value = false
                }
            )
    }
}
