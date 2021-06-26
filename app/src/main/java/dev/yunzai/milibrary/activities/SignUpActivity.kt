package dev.yunzai.milibrary.activities

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.base.hideKeyBoard
import dev.yunzai.milibrary.base.showAlertDialog
import dev.yunzai.milibrary.databinding.ActivitySignupBinding
import dev.yunzai.milibrary.util.getEmojiFilter
import dev.yunzai.milibrary.util.setOnSingleClickListener
import dev.yunzai.milibrary.viewmodels.SignUpViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : ViewBindingActivity<ActivitySignupBinding>() {
    override val layoutId: Int = R.layout.activity_signup
    private val signUpViewModel: SignUpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        initObserver()
    }

    private fun initView() {
        binding.userIdEditText.filters = arrayOf(getEmojiFilter())
        binding.passwordEditText.filters = arrayOf(getEmojiFilter())
        binding.passwordCheckEditText.filters = arrayOf(getEmojiFilter())

        binding.signUp.setOnSingleClickListener {
            signUp()
        }

        binding.passwordCheckEditText.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    hideKeyBoard()
                    signUp()
                }
            }
            return@setOnEditorActionListener true
        }

        useDefaultLoading(signUpViewModel)
    }

    private fun initObserver() {
        with(signUpViewModel) {
            errorMessage.observe(this@SignUpActivity) {
                if (it == null) return@observe
                showAlertDialog(getString(R.string.alert), getString(it), getString(R.string.ok)) { dialog ->
                    dialog.dismiss()
                }
            }

            errorMessageFromServerEvent.observe(this@SignUpActivity) {
                if (it == null) return@observe
                showAlertDialog(getString(R.string.alert), it, getString(R.string.ok)) { dialog ->
                    dialog.dismiss()
                }
            }

            signUpSuccessEvent.observe(this@SignUpActivity) {
                showAlertDialog(
                    getString(R.string.alert),
                    getString(R.string.sign_up_success),
                    getString(R.string.ok)
                ) { dialog ->
                    dialog.dismiss()
                    finish()
                }
            }
        }
    }

    private fun signUp() {
        val id = binding.userIdEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val passwordCheck = binding.passwordCheckEditText.text.toString().trim()
        signUpViewModel.signUp(id, password, passwordCheck)
    }
}
