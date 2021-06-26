package dev.yunzai.milibrary.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.base.hideStatusBar
import dev.yunzai.milibrary.databinding.ActivitySplashBinding

class SplashActivity : ViewBindingActivity<ActivitySplashBinding>() {
    override val layoutId: Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init() {
        Handler(Looper.getMainLooper()).postDelayed({
            goToLoginActivity()
        }, 2000L)
    }

    private fun goToLoginActivity() {
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        finish()
    }
}
