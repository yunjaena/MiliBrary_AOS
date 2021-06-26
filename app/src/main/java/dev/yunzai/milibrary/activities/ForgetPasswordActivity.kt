package dev.yunzai.milibrary.activities

import android.os.Bundle
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.base.showAlertDialog
import dev.yunzai.milibrary.databinding.ActivityForgetPasswordBinding
import dev.yunzai.milibrary.util.setOnSingleClickListener
import dev.yunzai.milibrary.viewmodels.ForgetPasswordViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgetPasswordActivity : ViewBindingActivity<ActivityForgetPasswordBinding>() {
    override val layoutId: Int = R.layout.activity_forget_password
    private val forgetPasswordViewModel: ForgetPasswordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        initObserver()
    }

    private fun initView() {
        binding.findPassword.setOnSingleClickListener {
            val id = binding.userIdEditText.text.toString().trim()
            forgetPasswordViewModel.findPassword(id)
        }
    }

    private fun initObserver() {
        with(forgetPasswordViewModel) {
            errorMessage.observe(this@ForgetPasswordActivity) {
                if (it == null) return@observe
                showAlertDialog(getString(R.string.alert), getString(it), getString(R.string.ok)) { dialog ->
                    dialog.dismiss()
                }
            }

            errorMessageFromServerEvent.observe(this@ForgetPasswordActivity) {
                if (it == null) return@observe
                showAlertDialog(getString(R.string.alert), it, getString(R.string.ok)) { dialog ->
                    dialog.dismiss()
                }
            }

            findPasswordSuccessEvent.observe(this@ForgetPasswordActivity) {
                showAlertDialog(
                    getString(R.string.alert),
                    getString(R.string.find_password_success),
                    getString(R.string.ok)
                ) { dialog ->
                    dialog.dismiss()
                    finish()
                }
            }
        }
    }
}
