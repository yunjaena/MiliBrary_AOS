package dev.yunzai.milibrary.activities

import android.content.Intent
import android.os.Bundle
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.base.showAlertDialog
import dev.yunzai.milibrary.databinding.ActivityLoginBinding
import dev.yunzai.milibrary.util.setOnSingleClickListener
import dev.yunzai.milibrary.viewmodels.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : ViewBindingActivity<ActivityLoginBinding>() {
    override val layoutId: Int = R.layout.activity_login
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        initObserver()
    }

    private fun initView() {
        binding.login.setOnSingleClickListener {
            val id = binding.userIdEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            loginViewModel.signIn(id, password)
        }

        binding.signUpButton.setOnSingleClickListener {
            Intent(this@LoginActivity, SignUpActivity::class.java).run {
                startActivity(this)
            }
        }

        useDefaultLoading(loginViewModel)
    }

    private fun initObserver() {
        with(loginViewModel) {
            errorMessage.observe(this@LoginActivity) {
                if (it == null) return@observe
                showAlertDialog(getString(R.string.alert), getString(it), getString(R.string.ok)) { dialog ->
                    dialog.dismiss()
                }
            }

            errorMessageFromServerEvent.observe(this@LoginActivity) {
                if (it == null) return@observe
                showAlertDialog(getString(R.string.alert), it, getString(R.string.ok)) { dialog ->
                    dialog.dismiss()
                }
            }

            loginSuccessEvent.observe(this@LoginActivity) {
                goToMainActivity()
            }
        }
    }

    private fun goToMainActivity() {
        Intent(this@LoginActivity, MainActivity::class.java).run {
            startActivity(this)
            finish()
        }
    }
}
