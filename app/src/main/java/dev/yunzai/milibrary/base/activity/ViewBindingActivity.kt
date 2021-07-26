package dev.yunzai.milibrary.base.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class ViewBindingActivity<T : ViewDataBinding> : BaseActivity() {
    @get:LayoutRes
    abstract val layoutId: Int
    private lateinit var _binding: T
    val binding: T
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutId)
        _binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        _binding.unbind()
        super.onDestroy()
    }
}
