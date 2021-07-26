package dev.yunzai.milibrary.util

import android.text.TextWatcher
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.orhanobut.logger.Logger
import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

fun <T> Single<T>.withThread(): Single<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.handleHttpException(): Single<T> {
    return this.doOnError {
        handleHttpException(it)
    }
}

fun Completable.withThread(): Completable {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Completable.handleUpdateAccessToken(): Completable {
    return this.doOnError {
        handleHttpException(it)
    }
}

fun Completable.handleHttpException(): Completable {
    return this.doOnError {
        handleHttpException(it)
    }
}

fun Completable.toSingleConvert(): Single<Boolean> {
    return this.toSingleDefault(true)
        .onErrorReturnItem(false)
}

fun <T> Observable<T>.withThread(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.handleHttpException(): Observable<T> {
    return this.doOnError {
        handleHttpException(it)
    }
}

fun <T> Single<T>.handleProgress(viewModel: ViewModelBase): Single<T> {
    return this.doOnSubscribe { viewModel.isLoading.postValue(true) }
        .doFinally { viewModel.isLoading.postValue(false) }
        .doOnDispose { viewModel.isLoading.postValue(false) }
}

fun Completable.handleProgress(viewModel: ViewModelBase): Completable {
    return this.doOnSubscribe { viewModel.isLoading.postValue(true) }
        .doFinally { viewModel.isLoading.postValue(false) }
        .doOnDispose { viewModel.isLoading.postValue(false) }
}

// TODO : 공통 HttpException 처리 (403 => 잘못된 접근)
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
