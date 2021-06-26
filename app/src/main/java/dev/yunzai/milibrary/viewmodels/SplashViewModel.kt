package dev.yunzai.milibrary.viewmodels

import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import dev.yunzai.milibrary.data.UserRepository
import dev.yunzai.milibrary.util.SingleLiveEvent
import dev.yunzai.milibrary.util.withThread
import io.reactivex.rxjava3.kotlin.addTo

class SplashViewModel(
    private val userRepository: UserRepository
) : ViewModelBase() {
    val isAutoLoginSuccessEvent = SingleLiveEvent<Boolean>()

    fun checkAutoLogin() {
        userRepository.refreshToken()
            .withThread()
            .subscribe({
                isAutoLoginSuccessEvent.value = true
            }, {
                isAutoLoginSuccessEvent.value = false
            }).addTo(compositeDisposable)
    }
}
