package dev.yunzai.milibrary.viewmodels

import androidx.annotation.StringRes
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import dev.yunzai.milibrary.data.UserRepository
import dev.yunzai.milibrary.data.model.toErrorResponse
import dev.yunzai.milibrary.util.SingleLiveEvent
import dev.yunzai.milibrary.util.handleProgress
import dev.yunzai.milibrary.util.isPasswordValid
import dev.yunzai.milibrary.util.toSha256
import dev.yunzai.milibrary.util.withThread

import io.reactivex.rxjava3.kotlin.addTo

class SignUpViewModel(
    private val userRepository: UserRepository
) : ViewModelBase() {
    val errorMessage = SingleLiveEvent<@StringRes Int>()
    val errorMessageFromServerEvent = SingleLiveEvent<String>()
    val signUpSuccessEvent = SingleLiveEvent<Boolean>()

    fun signUp(id: String, password: String, passwordCheck: String) {
        if (id.isNullOrEmpty() || password.isNullOrEmpty() || passwordCheck.isNullOrEmpty()) {
            errorMessage.value = R.string.signup_input_all_form_error
            return
        }

        if (!password.isPasswordValid()) {
            errorMessage.value = R.string.password_format_error
            return
        }

        if (password != passwordCheck) {
            errorMessage.value = R.string.signup_password_input_different_error
            return
        }

        userRepository.signUp(id, password.toSha256())
            .handleProgress(this)
            .withThread()
            .subscribe(
                {
                    signUpSuccessEvent.call()
                },
                {
                    val response = it.toErrorResponse()
                    if (response.isNullOrEmpty()) {
                        errorMessage.value = R.string.sign_up_fail
                        return@subscribe
                    }
                    response.forEach { message ->
                        errorMessageFromServerEvent.value = message
                    }
                }
            ).addTo(compositeDisposable)
    }
}
