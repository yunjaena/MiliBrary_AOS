package dev.yunzai.milibrary.activities

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.base.hideKeyBoard
import dev.yunzai.milibrary.base.showAlertDialog
import dev.yunzai.milibrary.databinding.ActivityResendEmailBinding
import dev.yunzai.milibrary.util.setOnSingleClickListener
import dev.yunzai.milibrary.viewmodels.ResendEmailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResendEmailActivity : ViewBindingActivity<ActivityResendEmailBinding>() {
    override val layoutId: Int = R.layout.activity_resend_email
    private val resendEmailViewModel: ResendEmailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        initObserver()
    }

    private fun initView() {
        binding.resendPassword.setOnSingleClickListener {
            sendEmail()
        }

        binding.userIdEditText.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    hideKeyBoard()
                    sendEmail()
                }
            }
            return@setOnEditorActionListener true
        }
    }

    private fun initObserver() {
        with(resendEmailViewModel) {
            errorMessage.observe(this@ResendEmailActivity) {
                if (it == null) return@observe
                showAlertDialog(getString(R.string.alert), getString(it), getString(R.string.ok)) { dialog ->
                    dialog.dismiss()
                }
            }

            errorMessageFromServerEvent.observe(this@ResendEmailActivity) {
                if (it == null) return@observe
                showAlertDialog(getString(R.string.alert), it, getString(R.string.ok)) { dialog ->
                    dialog.dismiss()
                }
            }

            resendEmailSuccessEvent.observe(this@ResendEmailActivity) {
                showAlertDialog(
                    getString(R.string.alert),
                    getString(R.string.resend_email_success),
                    getString(R.string.ok)
                ) { dialog ->
                    dialog.dismiss()
                    finish()
                }
            }
        }
    }

    private fun sendEmail() {
        val id = binding.userIdEditText.text.toString().trim()
        resendEmailViewModel.resendEmail(id)
    }
}
