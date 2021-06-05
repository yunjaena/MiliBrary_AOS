package dev.yunzai.milibrary.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class ViewModelBase : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()
    val isLoading = MutableLiveData<Boolean>()

    override fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onCleared()
    }
}
