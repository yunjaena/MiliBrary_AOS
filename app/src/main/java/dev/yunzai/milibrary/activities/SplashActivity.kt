package dev.yunzai.milibrary.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.base.hideStatusBar
import dev.yunzai.milibrary.databinding.ActivitySplashBinding
import dev.yunzai.milibrary.viewmodels.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : ViewBindingActivity<ActivitySplashBinding>() {
    override val layoutId: Int = R.layout.activity_splash
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        init()
    }

    private fun init() {
        splashViewModel.isAutoLoginSuccessEvent.observe(this) {
            when (it) {
                true -> goToMainActivity()
                else -> goToLoginActivity()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed(
            {
                splashViewModel.checkAutoLogin()
            },
            2000L
        )
    }

    private fun goToMainActivity() {
        Intent(this@SplashActivity, MainActivity::class.java).run {
            startActivity(this)
            finish()
        }
    }

    private fun goToLoginActivity() {
        Intent(this@SplashActivity, LoginActivity::class.java).run {
            startActivity(this)
            finish()
        }
    }
}
