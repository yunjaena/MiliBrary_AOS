package dev.yunzai.milibrary.activity

import android.os.Bundle
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.base.hideStatusBar
import dev.yunzai.milibrary.databinding.ActivityLoginBinding
import dev.yunzai.milibrary.util.setOnSingleClickListener

class LoginActivity : ViewBindingActivity<ActivityLoginBinding>() {
    override val layoutId: Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        init()
    }

    private fun init() {
        binding.login.setOnSingleClickListener {}
    }
}
