package dev.yunzai.milibrary.base.activity

import android.app.AlertDialog
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.appbar.BaseAppBar
import dev.yunzai.milibrary.base.hideProgressDialog
import dev.yunzai.milibrary.base.showProgressDialog
import dev.yunzai.milibrary.base.viewmodel.ViewModelBase
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseActivity : AppCompatActivity() {
    protected val compositeDisposable = CompositeDisposable()
    private var baseAppBar: BaseAppBar? = null
    private var progressDialog: AlertDialog? = null

    fun setBaseAppBar(title: String = "") {
        if (supportActionBar == null)
            throw IllegalStateException("Can not found supportActionBar")

        baseAppBar = BaseAppBar(this@BaseActivity, supportActionBar)
        baseAppBar?.setUpActionBar()
        setAppBarTitle(title)
    }

    fun appBarLeftButtonClicked(callback: (View) -> Unit) {
        baseAppBar?.leftButtonClickListener = callback
    }

    fun appBarRightButtonClicked(callback: (View) -> Unit) {
        baseAppBar?.rightButtonClickListener = callback
    }

    fun setAppBarTitle(title: String) {
        baseAppBar?.setUpActionBar()
        if (!title.isNullOrEmpty())
            baseAppBar?.setTitle(title)
    }

    fun setBackKey() {
        baseAppBar?.setLeftButtonDrawable(R.drawable.ic_arrow_back_black)
        appBarLeftButtonClicked {
            onBackPressed()
        }
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
        runOnUiThread {
            progressDialog?.hideProgressDialog()
            progressDialog = this@BaseActivity.showProgressDialog(useDim)
        }
    }

    fun hideProgress() {
        runOnUiThread {
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
