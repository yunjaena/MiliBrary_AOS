package dev.yunzai.milibrary.di

import dev.yunzai.milibrary.viewmodels.ForgetPasswordViewModel
import dev.yunzai.milibrary.viewmodels.LoginViewModel
import dev.yunzai.milibrary.viewmodels.ResendEmailViewModel
import dev.yunzai.milibrary.viewmodels.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ForgetPasswordViewModel(get()) }
    viewModel { ResendEmailViewModel(get()) }
}
