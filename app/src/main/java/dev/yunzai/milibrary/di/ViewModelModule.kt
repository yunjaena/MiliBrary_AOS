package dev.yunzai.milibrary.di

import dev.yunzai.milibrary.viewmodels.BookDetailViewModel
import dev.yunzai.milibrary.viewmodels.BookListViewModel
import dev.yunzai.milibrary.viewmodels.BookmarkViewModel
import dev.yunzai.milibrary.viewmodels.ForgetPasswordViewModel
import dev.yunzai.milibrary.viewmodels.HomeViewModel
import dev.yunzai.milibrary.viewmodels.LoginViewModel
import dev.yunzai.milibrary.viewmodels.MyPageViewModel
import dev.yunzai.milibrary.viewmodels.ResendEmailViewModel
import dev.yunzai.milibrary.viewmodels.ReviewViewModel
import dev.yunzai.milibrary.viewmodels.SignUpViewModel
import dev.yunzai.milibrary.viewmodels.SplashViewModel
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
    viewModel { BookmarkViewModel(get(), get()) }
    viewModel { MyPageViewModel(get()) }
}
