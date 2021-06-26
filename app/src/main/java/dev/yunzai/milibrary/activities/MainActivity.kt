package dev.yunzai.milibrary.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.orhanobut.logger.Logger
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.databinding.ActivityMainBinding
import dev.yunzai.milibrary.fragments.BookMarkFragment
import dev.yunzai.milibrary.fragments.HomeFragment
import dev.yunzai.milibrary.fragments.MyPageFragment
import dev.yunzai.milibrary.fragments.SearchFragment

class MainActivity : ViewBindingActivity<ActivityMainBinding>() {
    override val layoutId: Int = R.layout.activity_main
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            initFragment()
        initView()
    }

    private fun initFragment() {
        var fragment = supportFragmentManager.findFragmentByTag(HomeFragment.TAG)
        if (fragment == null) {
            fragment = HomeFragment.newInstance()
        }
        replaceFragment(fragment, HomeFragment.TAG)
    }

    private fun initView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var fragment: Fragment? = null
            var tag: String? = null
            when (item.itemId) {
                R.id.page_home -> {
                    tag = HomeFragment.TAG
                    fragment = supportFragmentManager.findFragmentByTag(tag)
                    if (fragment == null) {
                        fragment = HomeFragment.newInstance()
                    }
                }
                R.id.page_search -> {
                    tag = SearchFragment.TAG
                    fragment = supportFragmentManager.findFragmentByTag(tag)
                    if (fragment == null) {
                        fragment = SearchFragment.newInstance()
                    }
                }
                R.id.page_bookmark -> {
                    tag = BookMarkFragment.TAG
                    fragment = supportFragmentManager.findFragmentByTag(tag)
                    if (fragment == null) {
                        fragment = BookMarkFragment.newInstance()
                    }
                }
                R.id.page_my_page -> {
                    tag = MyPageFragment.TAG
                    fragment = supportFragmentManager.findFragmentByTag(tag)
                    if (fragment == null) {
                        fragment = MyPageFragment.newInstance()
                    }
                }
            }
            if (fragment != null && tag != null)
                replaceFragment(fragment, tag)

            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        if (fragment != currentFragment) {
            Logger.d("MainActivity Tag : $tag")
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit()
            currentFragment = fragment
        }
    }


}
