package dev.yunzai.milibrary.fragments

import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.fragment.ViewBindingFragment
import dev.yunzai.milibrary.databinding.FragmentMyPageBinding

class MyPageFragment : ViewBindingFragment<FragmentMyPageBinding>() {
    override val layoutId: Int = R.layout.fragment_my_page

    companion object {
        const val TAG = "MyPageFragment"

        @JvmStatic
        fun newInstance() = MyPageFragment()
    }
}
