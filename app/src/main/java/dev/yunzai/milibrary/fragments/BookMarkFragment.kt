package dev.yunzai.milibrary.fragments

import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.fragment.ViewBindingFragment
import dev.yunzai.milibrary.databinding.FragmentBookMarkBinding

class BookMarkFragment : ViewBindingFragment<FragmentBookMarkBinding>() {
    override val layoutId: Int = R.layout.fragment_book_mark

    companion object {
        const val TAG = "BookMarkFragment"

        @JvmStatic
        fun newInstance() = BookMarkFragment()
    }
}
