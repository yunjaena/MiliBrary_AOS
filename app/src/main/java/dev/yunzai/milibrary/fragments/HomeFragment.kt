package dev.yunzai.milibrary.fragments

import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.fragment.ViewBindingFragment
import dev.yunzai.milibrary.databinding.FragmentHomeBinding

class HomeFragment : ViewBindingFragment<FragmentHomeBinding>() {
    override val layoutId: Int = R.layout.fragment_home

    companion object {
        const val TAG = "HomeFragment"

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
