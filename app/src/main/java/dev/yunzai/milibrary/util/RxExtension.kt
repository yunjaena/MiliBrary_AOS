package dev.yunzai.milibrary.util

import android.text.TextWatcher
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.orhanobut.logger.Logger
import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import dev.yunzai.milibrary.data.UserRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableTransformer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

fun <T> Single<T>.withThread(): Single<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.handleUpdateAccessToken(): Single<T> {
    return this.compose(retryOnNotAuthorized<T>())
}

fun <T> Single<T>.handleHttpException(): Single<T> {
    return this.handleUpdateAccessToken()
        .doOnError {
            handleHttpException(it)
        }
}

fun Completable.withThread(): Completable {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Completable.handleUpdateAccessToken(): Completable {
    return this.compose(retryOnNotAuthorized())
}

fun Completable.handleHttpException(): Completable {
    return this.handleUpdateAccessToken()
        .doOnError {
            handleHttpException(it)
        }
}

fun Completable.toSingleConvert(): Single<Boolean> {
    return this.toSingleDefault(true)
        .onErrorReturnItem(false)
}

fun <T> Single<T>.handleProgress(viewModel: ViewModelBase): Single<T> {
    return this.doOnSubscribe { viewModel.isLoading.postValue(true) }
        .doOnError { viewModel.isLoading.postValue(false) }
        .doOnSuccess { viewModel.isLoading.postValue(false) }
        .doOnDispose { viewModel.isLoading.postValue(false) }
}

fun Completable.handleProgress(viewModel: ViewModelBase): Completable {
    return this.doOnSubscribe { viewModel.isLoading.postValue(true) }
        .doOnError { viewModel.isLoading.postValue(false) }
        .doOnComplete { viewModel.isLoading.postValue(false) }
        .doOnDispose { viewModel.isLoading.postValue(false) }
}

private fun <T> retryOnNotAuthorized(): SingleTransformer<T, T> {
    return SingleTransformer<T, T> { upstream ->
        upstream.onErrorResumeNext { throwable ->
            if (throwable is HttpException && throwable.code() == 401) {
                val userRepository: UserRepository by inject(UserRepository::class.java)
                userRepository.refreshToken()
                    .doOnError { error -> Logger.e("token update error : $error") }
                    .flatMap {
                        Completable.complete().andThen(upstream)
                    }
            } else
                Single.error(throwable)
        }
    }
}

private fun retryOnNotAuthorized(): CompletableTransformer {
    return CompletableTransformer { upstream ->
        upstream.onErrorResumeNext { throwable ->
            if (throwable is HttpException && throwable.code() == 401) {
                val userRepository: UserRepository by inject(UserRepository::class.java)
                userRepository.refreshToken()
                    .doOnError { error -> Logger.e("token update error : $error") }
                    .flatMapCompletable {
                        Completable.complete().andThen(upstream)
                    }
            } else
                Completable.error(throwable)
        }
    }
}

// TODO : 공통 HttpException 처리 (401 => 토큰 만료 또는 잘못된 토큰시 로그인 페이지로 이동)
private fun handleHttpException(throwable: Throwable) {
    if (throwable !is HttpException) return
    Logger.e("handle http exception : ${throwable.code()}")
    when (throwable.code()) {
    }
}

fun EditText.debounce(
    time: Long = 500L,
    timeUnit: TimeUnit = TimeUnit.MILLISECONDS
): Observable<String> {
    var textWatcher: TextWatcher? = null
    return Observable.create { emitter: ObservableEmitter<String> ->
        textWatcher = this.addTextChangedListener {
            if (this.isFocused)
                emitter.onNext(it.toString())
        }
    }.doOnDispose {
        this.removeTextChangedListener(textWatcher)
    }.debounce(time, timeUnit)
        .observeOn(AndroidSchedulers.mainThread())
}
