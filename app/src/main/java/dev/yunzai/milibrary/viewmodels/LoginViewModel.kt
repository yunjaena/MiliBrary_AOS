package dev.yunzai.milibrary.viewmodels

import androidx.annotation.StringRes
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import dev.yunzai.milibrary.data.UserRepository
import dev.yunzai.milibrary.data.model.toErrorResponse
import dev.yunzai.milibrary.util.SingleLiveEvent
import dev.yunzai.milibrary.util.handleProgress
import dev.yunzai.milibrary.util.toSha256
import dev.yunzai.milibrary.util.withThread
import io.reactivex.rxjava3.kotlin.addTo

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModelBase() {
    val errorMessage = SingleLiveEvent<@StringRes Int>()
    val errorMessageFromServerEvent = SingleLiveEvent<String>()
    val loginSuccessEvent = SingleLiveEvent<String>()

    fun signIn(id: String, password: String) {
        if (id.isNullOrEmpty() || password.isNullOrEmpty()) {
            errorMessage.value = R.string.login_fill_all_form_error
            return
        }

        userRepository.signIn(id, password.toSha256())
            .handleProgress(this)
            .withThread()
            .subscribe(
                {
                    loginSuccessEvent.call()
                },
                {
                    val response = it.toErrorResponse()
                    if (response.isNullOrEmpty()) {
                        errorMessage.value = R.string.login_fail_error
                        return@subscribe
                    }
                    response.forEach { message ->
                        errorMessageFromServerEvent.value = message
                    }
                }
            ).addTo(compositeDisposable)
    }
}
