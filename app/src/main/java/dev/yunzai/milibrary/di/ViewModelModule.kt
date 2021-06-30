package dev.yunzai.milibrary.di

import dev.yunzai.milibrary.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ForgetPasswordViewModel(get()) }
    viewModel { ResendEmailViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { BookDetailViewModel(get()) }
    viewModel { ReviewViewModel(get()) }
    viewModel { BookListViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { BookMarkViewModel(get()) }
}
