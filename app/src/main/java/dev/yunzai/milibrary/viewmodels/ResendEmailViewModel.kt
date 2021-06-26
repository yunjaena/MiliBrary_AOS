package dev.yunzai.milibrary.viewmodels

import androidx.annotation.StringRes
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import dev.yunzai.milibrary.data.UserRepository
import dev.yunzai.milibrary.data.model.toErrorResponse
import dev.yunzai.milibrary.util.SingleLiveEvent
import dev.yunzai.milibrary.util.handleProgress
import dev.yunzai.milibrary.util.withThread
import io.reactivex.rxjava3.kotlin.addTo

class ResendEmailViewModel(
    private val userRepository: UserRepository
) : ViewModelBase() {
    val errorMessage = SingleLiveEvent<@StringRes Int>()
    val errorMessageFromServerEvent = SingleLiveEvent<String>()
    val resendEmailSuccessEvent = SingleLiveEvent<String>()

    fun resendEmail(id: String) {
        if (id.isNullOrEmpty()) {
            errorMessage.value = R.string.id_empty_error
            return
        }

        userRepository.resendEmail(id)
            .handleProgress(this)
            .withThread()
            .subscribe(
                {
                    resendEmailSuccessEvent.call()
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
