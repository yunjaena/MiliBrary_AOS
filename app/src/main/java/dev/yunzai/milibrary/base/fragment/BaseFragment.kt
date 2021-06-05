package dev.yunzai.milibrary.base.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.yunzai.milibrary.base.hideProgressDialog
import dev.yunzai.milibrary.base.showProgressDialog
import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseFragment : Fragment() {
    protected val compositeDisposable = CompositeDisposable()
    private var progressDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun useDefaultLoading(viewModelBase: ViewModelBase, useDim: Boolean = true) {
        viewModelBase.isLoading.observe(this) { isLoading ->
            when (isLoading) {
                true -> showProgress(useDim)
                false -> hideProgress()
            }
        }
    }

    fun showProgress(useDim: Boolean = true) {
        requireActivity().runOnUiThread {
            progressDialog?.hideProgressDialog()
            progressDialog = requireActivity().showProgressDialog(useDim)
        }
    }

    fun hideProgress() {
        requireActivity().runOnUiThread {
            progressDialog?.hideProgressDialog()
            progressDialog = null
        }
    }

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
        super.onDestroy()
    }
}
