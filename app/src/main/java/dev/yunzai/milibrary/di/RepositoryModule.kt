package dev.yunzai.milibrary.di

import dev.yunzai.milibrary.constant.AUTH
import dev.yunzai.milibrary.constant.NO_AUTH
import dev.yunzai.milibrary.data.BookRepository
import dev.yunzai.milibrary.data.BookmarkRepository
import dev.yunzai.milibrary.data.ReviewRepository
import dev.yunzai.milibrary.data.UserRepository
import dev.yunzai.milibrary.data.local.UserLocalDataSource
import dev.yunzai.milibrary.data.remote.BookRemoteDataSource
import dev.yunzai.milibrary.data.remote.BookmarkRemoteDataSource
import dev.yunzai.milibrary.data.remote.ReviewRemoteDataSource
import dev.yunzai.milibrary.data.remote.UserRemoteDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single { UserLocalDataSource() }
    single { UserRemoteDataSource(get(named(NO_AUTH)), get(named(AUTH))) }
    single { UserRepository(get<UserLocalDataSource>(), get<UserRemoteDataSource>()) }
    single { BookRemoteDataSource(get(named(AUTH))) }
    single { BookRepository(get<BookRemoteDataSource>()) }
    single { ReviewRemoteDataSource(get(named(AUTH))) }
    single { ReviewRepository(get<ReviewRemoteDataSource>()) }
    single { BookmarkRemoteDataSource(get(named(AUTH))) }
    single { BookmarkRepository(get<BookmarkRemoteDataSource>()) }
}
